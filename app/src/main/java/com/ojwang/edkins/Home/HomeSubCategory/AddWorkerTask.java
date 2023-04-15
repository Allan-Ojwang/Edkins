package com.ojwang.edkins.Home.HomeSubCategory;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ojwang.edkins.R;

public class AddWorkerTask extends BottomSheetDialogFragment {
    public static final String TAG = "WORKER_ADD_TASK";
    public static final String EDIT_TAG = "WORKER_ADD_TASK_EDITED";
    private int id = -1;

    public interface OnWorkerInputListener {
        void sendInput(String name, int id_number, int number);

        void sendUpdateInput(int id, String name, int id_number, int number);
    }

    public OnWorkerInputListener onWorkerInputListener;

    public EditText evName, evIdNo, evNo;
    public Button save;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_worker_task, container, false);

        evName = view.findViewById(R.id.evName);
        evNo = view.findViewById(R.id.evNumber);
        evIdNo = view.findViewById(R.id.evIdNumber);
        save = view.findViewById(R.id.saveBtn);

//        Receives data from recycler view
        Bundle bundle = getArguments();
        if (bundle != null){
            id = bundle.getInt("ID");
            String name = bundle.getString("NAME");
            int number = bundle.getInt("NUMBER");
            int idNo = bundle.getInt("IdNUMBER");
            evName.setText(name);
            evNo.setText(String.valueOf(number));
            evIdNo.setText(String.valueOf(idNo));
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = evName.getText().toString();
                int number = Integer.parseInt(evNo.getText().toString());
                int idNo = Integer.parseInt(evIdNo.getText().toString());

                if (evName.getText().toString().trim().isEmpty() || evIdNo.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter the name and id number", Toast.LENGTH_SHORT).show();
                } else {
                    if (id != -1) {
                        onWorkerInputListener.sendUpdateInput(id,name,idNo, number);
                        getDialog().dismiss();
                    } else {
                        onWorkerInputListener.sendInput(name,idNo,number);
                        getDialog().dismiss();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onWorkerInputListener = (OnWorkerInputListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }
}

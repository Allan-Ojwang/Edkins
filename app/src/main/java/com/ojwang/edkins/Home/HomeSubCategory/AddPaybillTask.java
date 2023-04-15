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

public class AddPaybillTask extends BottomSheetDialogFragment {
    public static final String TAG = "PAYBILL_ADD_TASK";
    public static final String EDIT_TAG = "PAYBILL_ADD_TASK_EDITED";
    private int id = -1;

    public interface OnPaybillInputListener {
        void sendInput(String title, String body);

        void sendUpdateInput(int id, String title, String body);
    }

    public OnPaybillInputListener onPaybillInputListener;

    public EditText evTitle, evBody;
    public Button save;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_paybill_task, container, false);
        evTitle = view.findViewById(R.id.evTitle);
        evBody = view.findViewById(R.id.evBody);
        save = view.findViewById(R.id.saveBtn);

//        Receives data from recycler view
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt("ID");
            String title = bundle.getString("TITLE");
            String body = bundle.getString("BODY");
            EditText evTitle = view.findViewById(R.id.evTitle);
            EditText evBody = view.findViewById(R.id.evBody);
            evTitle.setText(title);
            evBody.setText(body);

        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = evTitle.getText().toString();
                String body = evBody.getText().toString();

                if (evTitle.getText().toString().trim().isEmpty() || evBody.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter the title and body", Toast.LENGTH_SHORT).show();
                } else {
                    if (id != -1) {
                        onPaybillInputListener.sendUpdateInput(id, title, body);
                        getDialog().dismiss();
                    } else {
                        onPaybillInputListener.sendInput(title, body);
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
            onPaybillInputListener = (OnPaybillInputListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }
}

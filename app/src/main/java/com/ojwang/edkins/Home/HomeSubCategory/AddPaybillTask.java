package com.ojwang.edkins.Home.HomeSubCategory;

import android.content.Context;
import android.content.DialogInterface;
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
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ojwang.edkins.Home.ConfirmAlertDialog;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.PaybillAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.Objects;

public class AddPaybillTask extends BottomSheetDialogFragment {
    public static final String TAG = "PAYBILL_ADD_TASK";
    public static final String EDIT_TAG = "PAYBILL_ADD_TASK_EDITED";

    public interface OnPaybillInputListener {
        void sendInput(String title, String body);

        void sendUpdateInput(int id, String title, String body);
    }

    public OnPaybillInputListener onPaybillInputListener;

    public EditText evTitle, evBody;
    public Button save, delete;

    private int adapterPos;
    private int id = -1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String title,body;
        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        View view = inflater.inflate(R.layout.add_paybill_task, container, false);
        evTitle = view.findViewById(R.id.evTitle);
        evBody = view.findViewById(R.id.evBody);
        save = view.findViewById(R.id.saveBtn);
        delete = view.findViewById(R.id.deleteBtn);

        PaybillAdapter paybillAdapter = new PaybillAdapter();

//        Receives data from recycler view
        Bundle bundle = getArguments();
        if (bundle != null) {
            adapterPos = bundle.getInt("ADAPTERPOS");
            id = bundle.getInt("ID");
            title = bundle.getString("TITLE");
            body = bundle.getString("BODY");
            evTitle.setText(title);
            evBody.setText(body);

        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (evTitle.getText().toString().trim().isEmpty() || evBody.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter the title and body", Toast.LENGTH_SHORT).show();
                } else {
                    String title = evTitle.getText().toString();
                    String body = evBody.getText().toString();
                    if (id != -1) {
                        onPaybillInputListener.sendUpdateInput(id, title, body);
                        Objects.requireNonNull(getDialog()).dismiss();
                    } else {
                        onPaybillInputListener.sendInput(title, body);
                        Objects.requireNonNull(getDialog()).dismiss();
                    }
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmAlertDialog confirmAlertDialog = new ConfirmAlertDialog(getContext(), "Are you sure you want to delete?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Delete
                                if (id != -1){
                                    mainViewModel.deletePaybill(paybillAdapter.getPaybillAt(adapterPos));
                                    Objects.requireNonNull(getDialog()).dismiss();
                                } else {
                                    Toast.makeText(getContext(), "Please select a paybill to delete", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //Do nothing
                                break;
                        }

                    }
                });
                confirmAlertDialog.showDialog();
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

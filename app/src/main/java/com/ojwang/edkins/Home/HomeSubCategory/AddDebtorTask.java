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
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.DebtorAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.Objects;

public class AddDebtorTask extends BottomSheetDialogFragment {
    public static final String TAG = "DEBTOR_ADD_TASK";
    public static final String EDIT_TAG = "DEBTOR_ADD_TASK_EDITED";
    public OnDebtorInputListener onDebtorInputListener;

    public interface OnDebtorInputListener {
        void sendInputDebtor(String name, String reason, int amount);

        void sendUpdateInputDebtor(int id, String name, String reason,int amount, Boolean status);

    }

    public EditText Evname, Evreason, Evamount;
    public Button save, delete;

    private int adapterPos;
    private int id = -1;

    private Boolean status;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String name,reason;
        int amount;
        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        View view = inflater.inflate(R.layout.add_debtor_creditor_task,container,false);
        Evname = view.findViewById(R.id.name);
        Evreason = view.findViewById(R.id.reason);
        Evamount = view.findViewById(R.id.amount);
        save = view.findViewById(R.id.saveBtn);
        delete = view.findViewById(R.id.deleteBtn);

        DebtorAdapter debtorAdapter = new DebtorAdapter();

        Bundle bundle = getArguments();
        if (bundle != null) {
            adapterPos = bundle.getInt("ADAPTERPOS");
            id = bundle.getInt("ID");
            name = bundle.getString("NAME");
            reason = bundle.getString("REASON");
            amount =bundle.getInt("AMOUNT");
            status = bundle.getBoolean("STATUS");
            Evname.setText(name);
            Evreason.setText(reason);
            Evamount.setText(String.valueOf(amount));

        }
        if (status != null && status){
            delete.setEnabled(false);
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Evamount.getText().toString().trim().isEmpty() || Evreason.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter all information", Toast.LENGTH_SHORT).show();
                } else {
                    String name = Evname.getText().toString();
                    String reason = Evreason.getText().toString();
                    int amount = Integer.parseInt(Evamount.getText().toString());
                    if(id != -1){
                        onDebtorInputListener.sendUpdateInputDebtor(id,name, reason,amount,status);
                        Objects.requireNonNull(getDialog()).dismiss();
                    } else {
                        onDebtorInputListener.sendInputDebtor(name, reason,amount);
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
                                    mainViewModel.deleteDebtor(debtorAdapter.getDebtorAt(adapterPos));
                                    Objects.requireNonNull(getDialog()).dismiss();
                                } else {
                                    Toast.makeText(getContext(), "Please select a debtor to delete", Toast.LENGTH_SHORT).show();
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
            onDebtorInputListener = (OnDebtorInputListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }
}

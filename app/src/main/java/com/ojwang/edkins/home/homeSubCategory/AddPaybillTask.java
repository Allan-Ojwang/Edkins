package com.ojwang.edkins.home.homeSubCategory;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ojwang.edkins.home.ConfirmAlertDialog;
import com.ojwang.edkins.home.homeSubCategory.model.PaybillModel;
import com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter.PaybillAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.viewModel.MainViewModel;

import java.util.Objects;

public class AddPaybillTask extends BottomSheetDialogFragment {
    public static final String TAG = "PAYBILL_ADD_TASK";
    public static final String EDIT_TAG = "PAYBILL_ADD_TASK_EDITED";


    public EditText evName, evTillPay, evAccNO;
    public Button save, delete;

    public RadioGroup radioGroup;
    public RadioButton radioButtonTill;
    public RadioButton radioButtonPaybill;
    private MainViewModel mainViewModel;
    public String status ="TILL";

    private int adapterPos;
    private int id = -1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String nameRec,tillPayRec,accRec;

        View view = inflater.inflate(R.layout.add_paybill_task, container, false);
        evName = view.findViewById(R.id.evName);
        evTillPay = view.findViewById(R.id.evTillPay);
        evAccNO = view.findViewById(R.id.evAccount);
        save = view.findViewById(R.id.saveBtn);
        delete = view.findViewById(R.id.deleteBtn);
        radioGroup = view.findViewById(R.id.radioGroup);
        radioButtonPaybill = view.findViewById(R.id.radioButtonPaybill);
        radioButtonTill = view.findViewById(R.id.radioButtonTill);
        PaybillAdapter paybillAdapter = new PaybillAdapter();

        if (id == -1 ){
            radioButtonTill.setChecked(true);
        }

//        Receives data from recycler view
        Bundle bundle = getArguments();
        if (bundle != null) {
            adapterPos = bundle.getInt("ADAPTERPOS");
            id = bundle.getInt("ID");
            nameRec = bundle.getString("NAME");
            tillPayRec = bundle.getString("TILLPAY");
            status = bundle.getString("STATUS");
            accRec = bundle.getString("ACCNO");

            if (Objects.equals(status, "PAYBILL")){
                evAccNO.setVisibility(View.VISIBLE);
                evAccNO.setText(accRec);
                radioButtonPaybill.setChecked(true);

            } else if (Objects.equals(status, "TILL")) {
                radioButtonTill.setChecked(true);
            }

            evName.setText(nameRec);
            evTillPay.setText(tillPayRec);

        }

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = view.findViewById(checkedId);

                if (checkedId == R.id.radioButtonTill){
                    evName.setHint("Enter till name");
                    evTillPay.setHint("Enter till number");
                    evAccNO.setVisibility(View.GONE);
                } else if (checkedId == R.id.radioButtonPaybill) {
                    evName.setHint("Enter paybill name");
                    evTillPay.setHint("Enter paybill number");
                    evAccNO.setVisibility(View.VISIBLE);
                }
                status = radioButton.getText().toString();
            }
        });



        save.setOnClickListener(v -> {

            if (evName.getText().toString().trim().isEmpty() || evTillPay.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "Please enter the name and number", Toast.LENGTH_SHORT).show();
            } else {
                String name= evName.getText().toString();
                String tillPay = evTillPay.getText().toString();
                String accNo = evAccNO.getText().toString();

                PaybillModel paybillModel = new PaybillModel(name,tillPay,status);
                if (id != -1) {
                    paybillModel.setId(id);
                    if (Objects.equals(status, "TILL")){
                        mainViewModel.updatePaybill(paybillModel);
                    } else if (Objects.equals(status, "PAYBILL")) {
                        paybillModel.setAccNo(accNo);
                        mainViewModel.updatePaybill(paybillModel);
                    }
                } else {
                    if (Objects.equals(status, "TILL")){
                        mainViewModel.insertPaybill(paybillModel);
                    } else if (Objects.equals(status, "PAYBILL")) {
                        paybillModel.setAccNo(accNo);
                        mainViewModel.insertPaybill(paybillModel);
                    }
                }
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmAlertDialog confirmAlertDialog = new ConfirmAlertDialog(getContext(), "Are you sure you want to delete this paybill?", new DialogInterface.OnClickListener() {
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


}

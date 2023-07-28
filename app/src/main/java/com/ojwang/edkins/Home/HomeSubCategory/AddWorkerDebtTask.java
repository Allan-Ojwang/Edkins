package com.ojwang.edkins.Home.HomeSubCategory;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ojwang.edkins.Home.ConfirmAlertDialog;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.WorkerDebtAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.Calendar;
import java.util.Objects;

public class AddWorkerDebtTask extends BottomSheetDialogFragment {
    public static final String TAG = "WORKER_DEBT_ADD_TASK";
    public static final String EDIT_TAG = "WORKER_DEBT_ADD_TASK_EDITED";

    private int id = -1;
    public int setDay,setYear;
    public String setMonth,dStatus;
    private int adapterPos;

    public interface OnWorkerDebtInputListener {
        void sendInput(String date, int amount, String dStatus);

        void sendUpdateInput(int id, String date, int amount,String dStatus);
    }

    public OnWorkerDebtInputListener onWorkerDebtInputListener;
    public EditText evAmount;
    public Button saveBtn,dateBtn,deleteBtn;
    public RadioGroup radioGroup;
    public RadioButton radioButtonLoan;
    public RadioButton radioButtonSaving;
    private DatePickerDialog datePickerDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_worker_debt_task,container,false);
        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        evAmount = view.findViewById(R.id.evAmount);
        saveBtn = view.findViewById(R.id.saveBtn);
        deleteBtn = view.findViewById(R.id.deleteBtn);
        dateBtn = view.findViewById(R.id.datebtn);
        radioGroup = view.findViewById(R.id.radioGroup);
        radioButtonSaving = view.findViewById(R.id.radioButtonSaving);
        radioButtonLoan = view.findViewById(R.id.radioButtonLoan);

        String date;
        int amount;

        WorkerDebtAdapter workerDebtAdapter = new WorkerDebtAdapter();

        dateBtn.setText(getTodaysDate());

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                setMonth =  makeDateString(month);
                setDay = dayOfMonth;
                setYear = year;
                String formatted = setDay+" "+setMonth+" "+setYear;
                dateBtn.setText(formatted);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = android.R.style.Theme_Material_Dialog_Alert;;
        datePickerDialog = new DatePickerDialog(getContext(), style,dateSetListener,year,month,day);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        //        Receives data from recycler view
        Bundle bundle = getArguments();
        if (bundle != null) {
            adapterPos = bundle.getInt("ADAPTERPOS");
            id = bundle.getInt("ID");
            date = bundle.getString("DATE");
            amount = bundle.getInt("AMOUNT");
            dStatus = bundle.getString("DSTATUS");

            if (Objects.equals(dStatus, "Loan")){
                radioButtonLoan.setChecked(true);
            } else if (Objects.equals(dStatus, "Saving")) {
                radioButtonSaving.setChecked(true);
            }
            evAmount.setText(String.valueOf(amount));
            dateBtn.setText(date);

        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = view.findViewById(checkedId);
                dStatus = radioButton.getText().toString();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (evAmount.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Please enter the amount", Toast.LENGTH_SHORT).show();
                } else {
                    String dateEv = dateBtn.getText().toString();
                    int amountEv = Integer.parseInt(evAmount.getText().toString());
                    if (id != -1){
                        onWorkerDebtInputListener.sendUpdateInput(id,dateEv,amountEv,dStatus);
                        Objects.requireNonNull(getDialog()).dismiss();
                    } else{
                        onWorkerDebtInputListener.sendInput(dateEv,amountEv,dStatus);
                        Objects.requireNonNull(getDialog()).dismiss();
                    }
                }


            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmAlertDialog confirmAlertDialog = new ConfirmAlertDialog(getContext(), "Are you sure you want to delete this worker's debt?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Delete
                                if (id != -1){
                                    mainViewModel.deleteWorkerDebt(workerDebtAdapter.getWorkerDebtAt(adapterPos));
                                    Objects.requireNonNull(getDialog()).dismiss();
                                } else {
                                    Toast.makeText(getContext(), "Please select a debt to delete", Toast.LENGTH_SHORT).show();
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

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return String.valueOf(day+" ")+makeDateString(month)+String.valueOf(" "+year);
    }


    private String makeDateString(int month) {
        return getMonthFormat(month);
    }

    private String getMonthFormat(int month) {
        if (month == 1){
            return "JAN";
        }
        if (month == 2){
            return "FEB";
        }
        if (month == 3){
            return "MAR";
        }
        if (month == 4){
            return "APR";
        }
        if (month == 5){
            return "MAY";
        }
        if (month == 6){
            return "JUN";
        }
        if (month == 7){
            return "JUL";
        }
        if (month == 8){
            return "AUG";
        }
        if (month == 9){
            return "SEP";
        }
        if (month == 10){
            return "OCT";
        }
        if (month == 11){
            return "NOV";
        }
        if (month == 12){
            return "DEC";
        }
        return "JAN";
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onWorkerDebtInputListener = (OnWorkerDebtInputListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }
}

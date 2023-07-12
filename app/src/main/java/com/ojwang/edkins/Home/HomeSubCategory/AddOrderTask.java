package com.ojwang.edkins.Home.HomeSubCategory;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ojwang.edkins.R;

import java.util.Calendar;
import java.util.Objects;

public class AddOrderTask extends BottomSheetDialogFragment {
    public static final String TAG = "ORDER_ADD_TASK";
    private int id = -1;

    public int setDay,setYear;
    public String setMonth;
    public interface OnOrderInputListener {
        void sendInput(int year, String month, int date);
    }

    public OnOrderInputListener onOrderInputListener;

    public Button save, dateBtn;
    private DatePickerDialog datePickerDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_order_task,container,false);
        save = view.findViewById(R.id.saveBtn);
        dateBtn = view.findViewById(R.id.datebtn);
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = dateBtn.getText().toString();
                String [] dateParts= date.split(" ");
                int daySet = Integer.parseInt(dateParts[0]);
                String monthSet = dateParts[1];
                int yearSet = Integer.parseInt(dateParts[2]);
                if (id != -1){
//                        TODO : update order
                    Objects.requireNonNull(getDialog()).dismiss();
                } else{
                    onOrderInputListener.sendInput(yearSet,monthSet,daySet);
                    Objects.requireNonNull(getDialog()).dismiss();
                }
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
            onOrderInputListener = (OnOrderInputListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }
}

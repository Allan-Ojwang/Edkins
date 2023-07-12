package com.ojwang.edkins.Home.HomeSubCategory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ojwang.edkins.Home.ConfirmAlertDialog;
import com.ojwang.edkins.Home.HomeSubCategory.Model.ToOrderListModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.ToOrderModel;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.ToOrderAdapter;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.ToOrderListAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class ToOrderSub extends AppCompatActivity implements AddOrderListTask.OnOrderListInputListener{


    private MainViewModel mainViewModel;
    private String Item,setMonth,dStatus;

    private int orderId, Quantity,Id,adapPos,orderNumbStatus,orderNumb,setDay,setYear;

    @Override
    public void sendInput(String item, int quantity) {
        Item = item;
        Quantity = quantity;
        ToOrderListModel toOrderListModel = new ToOrderListModel(Item,Quantity,orderId);
        mainViewModel.insertToOrderList(toOrderListModel);
    }

    @Override
    public void sendUpdateInput(int id, String item, int quantity) {
        Id = id;
        Item = item;
        Quantity = quantity;
        ToOrderListModel toOrderListModel = new ToOrderListModel(Item,Quantity,orderId);
        toOrderListModel.setId(Id);
        mainViewModel.updateToOrderList(toOrderListModel);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_order_sub);

//        Intent intent = getIntent();
//        orderId = intent.getIntExtra("orderId",0);
//        adapPos = intent.getIntExtra("adapPos",1);
//
//        TextView orderTv = findViewById(R.id.orderId);
//        orderTv.setText(String.valueOf(orderId));

        ImageButton backBtn = findViewById(R.id.backBtn);
        ImageButton deleteBtn = findViewById(R.id.deleteBtn);
        ImageButton saveBtn = findViewById(R.id.saveBtn);
        ImageButton addBtn = findViewById(R.id.addBtn);
        Button dateBtn = findViewById(R.id.datebtn);
        DatePickerDialog datePickerDialog;
//
//        RecyclerView recyclerView = findViewById(R.id.toOrderSubRecycleView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
//
//        ToOrderListAdapter toOrderListAdapter = new ToOrderListAdapter();
//        ToOrderAdapter toOrderAdapter = new ToOrderAdapter();
//        recyclerView.setAdapter(toOrderListAdapter);
//
//        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
//
//        mainViewModel.getToOrderListData(orderId).observe(this, new Observer<List<ToOrderListModel>>() {
//            @Override
//            public void onChanged(List<ToOrderListModel> toOrderListModels) {
//                ToOrderModel toOrderModel = toOrderAdapter.getOrderAt(adapPos);
//                toOrderListAdapter.setOrderNotes(toOrderListModels);
////                String newOrderStatus;
////                Log.e("TestOrderNumb", String.valueOf(orderNumb));
////                Log.e("TestOrderNumbStatus", String.valueOf(orderNumbStatus));
////                if (orderNumbStatus == orderNumb && orderNumb > 0){
////                    newOrderStatus = "Done";
////                    toOrderModel.setStatus(newOrderStatus);
////                    mainViewModel.updateToOrder(toOrderModel);
////                } else {
////                    newOrderStatus = "Processing";
////                    toOrderModel.setStatus(newOrderStatus);
////                    mainViewModel.updateToOrder(toOrderModel);
////                }
//                mainViewModel.getNumbOfOrderStatus(orderId).observe(ToOrderSub.this, new Observer<Integer>() {
//                    @Override
//                    public void onChanged(Integer integer) {
//                        orderNumbStatus = integer;
//                    }
//                });
//                mainViewModel.getNumbOfOrder(orderId).observe(ToOrderSub.this, new Observer<Integer>() {
//                    @Override
//                    public void onChanged(Integer integer) {
//                        orderNumb = integer;
//                    }
//                });
//
//            }
//        });




        backBtn.setOnClickListener(v -> {
            finish();
        });

        addBtn.setOnClickListener(v -> {

        });

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
        datePickerDialog = new DatePickerDialog(this, style,dateSetListener,year,month,day);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

//        deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ConfirmAlertDialog confirmAlertDialog = new ConfirmAlertDialog(ToOrderSub.this, "Are you sure you want to delete this whole order?", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which){
//                            case DialogInterface.BUTTON_POSITIVE:
//                                mainViewModel.deleteToOrder(toOrderAdapter.getOrderAt(adapPos));
//                                finish();
//                                break;
//                            case DialogInterface.BUTTON_NEGATIVE:
//                                break;
//
//                        }
//                    }
//                });
//                confirmAlertDialog.showDialog();
//
//            }
//        });
//
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                int position = viewHolder.getAdapterPosition();
//                ToOrderListModel toOrderListModel = toOrderListAdapter.getOrderListAt(position);
//                boolean newStatus = !toOrderListModel.isoStatus();
//                toOrderListModel.setoStatus(newStatus);
//                mainViewModel.updateToOrderList(toOrderListModel);
//
//
//            }
//
//        }).attachToRecyclerView(recyclerView);

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

}
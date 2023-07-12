package com.ojwang.edkins.Home.HomeSubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ojwang.edkins.Home.HomeSubCategory.Model.ToOrderListModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.ToOrderModel;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.ToOrderAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.List;

public class ToOrder extends AppCompatActivity implements AddOrderTask.OnOrderInputListener{

    public int Year, Date;
    public int orderId, adapPos,orderNumbStatus,orderNumb;
    public String Month, Title;
    @Override
    public void sendInput(int year, String month, int date) {
        Year = year;
        Month = month;
        Date = date;
        ToOrderModel toOrderModel = new ToOrderModel(Year,Month,Date);
        mainViewModel.insertToOrder(toOrderModel);


    }
    private MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_order);

        FloatingActionButton addBtn = findViewById(R.id.addfloatingActionButton);
        ImageButton backBtn = findViewById(R.id.backBtn);

//        RecyclerView recyclerView = findViewById(R.id.toOrderRecycleView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
//
//        ToOrderAdapter toOrderAdapter = new ToOrderAdapter();
//        recyclerView.setAdapter(toOrderAdapter);
//
//        Intent intentPass = getIntent();
//        orderNumb = intentPass.getIntExtra("orderNumb",0);
//        orderNumbStatus = intentPass.getIntExtra("orderNumbStatus",0);
//
//        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
//        mainViewModel.getToOrderData().observe(this, new Observer<List<ToOrderModel>>() {
//            @Override
//            public void onChanged(List<ToOrderModel> toOrderModels) {
//                toOrderAdapter.setOrderNotes(toOrderModels);
//            }
//        });
//
//        if (orderId > 0){
//            mainViewModel.getToOrderListData(orderId).observe(this, new Observer<List<ToOrderListModel>>() {
//                @Override
//                public void onChanged(List<ToOrderListModel> toOrderListModels) {
//                    ToOrderModel toOrderModel = toOrderAdapter.getOrderAt(adapPos);
//                    String newOrderStatus;
//                    if (orderNumbStatus == orderNumb && orderNumb > 0){
//                    newOrderStatus = "Done";
//                    toOrderModel.setStatus(newOrderStatus);
//                    mainViewModel.updateToOrder(toOrderModel);
//                } else {
//                    newOrderStatus = "Processing";
//                    toOrderModel.setStatus(newOrderStatus);
//                    mainViewModel.updateToOrder(toOrderModel);
//                }
//                }
//            });
//        }

        addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ToOrder.this, ToOrderSub.class);
            startActivity(intent);
        });

        backBtn.setOnClickListener(v -> finish());

//        toOrderAdapter.setOnClickListener(new ToOrderAdapter.OnItemClickListener() {
//            @Override
//            public void OnClick(ToOrderModel toOrderModel, int position) {
//                orderId = toOrderModel.getOrderId();
//                adapPos = position;
//                Intent intent = new Intent(ToOrder.this,ToOrderSub.class);
//                intent.putExtra("orderId", orderId);
//                intent.putExtra("adapPos",adapPos);
//                startActivity(intent);
//            }
//        });

    }



}
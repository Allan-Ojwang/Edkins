package com.ojwang.edkins.home.homeSubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ojwang.edkins.home.homeSubCategory.model.ToOrderModel;
import com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter.ToOrderAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.viewModel.MainViewModel;

import java.util.List;

public class ToOrder extends AppCompatActivity {

    public int  adapPos,orderNumbStatus,orderNumb,day,year;
    private String month;
    private Long orderId;
    private MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_order);

        FloatingActionButton addBtn = findViewById(R.id.addfloatingActionButton);
        ImageButton backBtn = findViewById(R.id.backBtn);

        RecyclerView recyclerView = findViewById(R.id.toOrderRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        ToOrderAdapter toOrderAdapter = new ToOrderAdapter();
        recyclerView.setAdapter(toOrderAdapter);

        Intent intentPass = getIntent();
        orderNumb = intentPass.getIntExtra("orderNumb",0);
        orderNumbStatus = intentPass.getIntExtra("orderNumbStatus",0);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getToOrderData().observe(this, new Observer<List<ToOrderModel>>() {
            @Override
            public void onChanged(List<ToOrderModel> toOrderModels) {
                toOrderAdapter.setOrderNotes(toOrderModels);
            }
        });


        addBtn.setOnClickListener(v -> {
            AddOrderTask addOrderTask = new AddOrderTask();
            addOrderTask.show(getSupportFragmentManager(),AddOrderTask.TAG);
        });

        backBtn.setOnClickListener(v -> finish());

        toOrderAdapter.setOnClickListener(new ToOrderAdapter.OnItemClickListener() {
            @Override
            public void OnClick(ToOrderModel toOrderModel, int position) {
                orderId = (long) toOrderModel.getOrderId();
                month = toOrderModel.getMonth();
                year = toOrderModel.getYear();
                day = toOrderModel.getDate();
                adapPos = position;
                Intent intent = new Intent(ToOrder.this,ToOrderSub.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("adapPos",adapPos);
                intent.putExtra("day",day);
                intent.putExtra("month",month);
                intent.putExtra("year",year);
                startActivity(intent);
            }
        });

    }



}
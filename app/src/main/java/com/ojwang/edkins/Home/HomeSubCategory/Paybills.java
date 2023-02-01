package com.ojwang.edkins.Home.HomeSubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ojwang.edkins.Home.HomeSubCategory.Model.PaybillModel;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.PaybillAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.List;

public class Paybills extends AppCompatActivity implements AddPaybillTask.OnPaybillInputListener {


    public String Title;
    public String Body;
    private MainViewModel mainViewModel;

    @Override
    public void sendInput(String title, String body) {
        Title = title;
        Body = body;
        PaybillModel paybillModel = new PaybillModel(Title,Body);
        mainViewModel.insertPaybill(paybillModel);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paybills);

        FloatingActionButton addBtn = findViewById(R.id.addfloatingActionButton);
        ImageButton backBtn = findViewById(R.id.backBtn);

        RecyclerView recyclerView = findViewById(R.id.paybillRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        PaybillAdapter paybillAdapter = new PaybillAdapter();
        recyclerView.setAdapter(paybillAdapter);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getPaybillData().observe(this, new Observer<List<PaybillModel>>() {
            @Override
            public void onChanged(List<PaybillModel> paybillModels) {
                paybillAdapter.setPaybillNotes(paybillModels);

            }
        });

        addBtn.setOnClickListener(v -> {
            AddPaybillTask addPaybills = new AddPaybillTask();
            addPaybills.show(getSupportFragmentManager(),AddPaybillTask.TAG);
        });

        backBtn.setOnClickListener(v -> finish());

    }


}
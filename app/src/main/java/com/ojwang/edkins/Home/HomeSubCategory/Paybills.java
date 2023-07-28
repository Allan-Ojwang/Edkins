package com.ojwang.edkins.Home.HomeSubCategory;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ojwang.edkins.Home.HomeSubCategory.Model.PaybillModel;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.PaybillAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.List;

public class Paybills extends AppCompatActivity {

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paybills);

        FloatingActionButton addBtn = findViewById(R.id.addfloatingActionButton);
        ImageButton backBtn = findViewById(R.id.backBtn);

        RecyclerView recyclerView = findViewById(R.id.paybillRecycleView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
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

//        TODO: api to send to mpesa for paybill and till

        paybillAdapter.setOnClickListener(new PaybillAdapter.OnItemClickListener() {
            @Override
            public void OnClick(PaybillModel paybillModel, int position) {
                AddPaybillTask addPaybillTask = new AddPaybillTask();
                Bundle bundle = new Bundle();
                bundle.putInt("ID",paybillModel.getId());
                bundle.putString("NAME",paybillModel.getName());
                bundle.putString("TILLPAY",paybillModel.getTillPay());
                bundle.putString("STATUS",paybillModel.getStatus());
                bundle.putString("ACCNO",paybillModel.getAccNo());
                bundle.putInt("ADAPTERPOS",position);
                addPaybillTask.setArguments(bundle);
                addPaybillTask.show(getSupportFragmentManager(),AddPaybillTask.EDIT_TAG);

            }
        });

        SearchView searchView = findViewById(R.id.paybillSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LiveData<List<PaybillModel>> filterPaybill= mainViewModel.searchPaybill("%" + newText + "%");
                filterPaybill.observe(Paybills.this, new Observer<List<PaybillModel>>() {
                    @Override
                    public void onChanged(List<PaybillModel> paybillModels) {
                        paybillAdapter.setPaybillNotes(paybillModels);
                    }
                });
                return true;
            }
        });
    }
}
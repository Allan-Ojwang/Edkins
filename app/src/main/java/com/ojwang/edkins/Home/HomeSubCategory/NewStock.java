package com.ojwang.edkins.Home.HomeSubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ojwang.edkins.Home.HomeSubCategory.Model.PaybillModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.StockModel;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.CreditorAdapter;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.NewStockAdapter;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.PaybillAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.List;

public class NewStock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_stock);
        FloatingActionButton addFloatingActionButton = findViewById(R.id.addfloatingActionButton);
        ImageButton backBtn = findViewById(R.id.backBtn);

        RecyclerView recyclerView = findViewById(R.id.stockRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        NewStockAdapter newStockAdapter = new NewStockAdapter();
        recyclerView.setAdapter(newStockAdapter);

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getStockData().observe(this, new Observer<List<StockModel>>() {
            @Override
            public void onChanged(List<StockModel> stockModels) {
                newStockAdapter.setStockModels(stockModels);
            }
        });
        newStockAdapter.setOnClickListener(new NewStockAdapter.OnItemClickListener() {
            @Override
            public void OnClick(StockModel stockModel, int position) {
                NewStockTask newStockTask = new NewStockTask();
                Bundle bundle = new Bundle();
                bundle.putInt("ID",stockModel.getId());
                bundle.putString("PRODUCT-NAME",stockModel.getProductName());
                bundle.putInt("SELLING-PRICE",stockModel.getSellingPrice());
                bundle.putInt("ADAPTERPOS",position);
                newStockTask.setArguments(bundle);
                newStockTask.show(getSupportFragmentManager(),NewStockTask.EDIT_TAG);

            }
        });

        backBtn.setOnClickListener(v -> finish());
        addFloatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(NewStock.this, NewStockSub.class);
            startActivity(intent);
        });

        SearchView searchView = findViewById(R.id.stockSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LiveData<List<StockModel>> filterStock= mainViewModel.searchStock("%" + newText + "%");
                filterStock.observe(NewStock.this, new Observer<List<StockModel>>() {
                    @Override
                    public void onChanged(List<StockModel> stockModels) {
                        newStockAdapter.setStockModels(stockModels);
                    }
                });
                return true;
            }
        });
    }
}
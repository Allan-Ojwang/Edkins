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
import android.widget.ImageButton;

import com.ojwang.edkins.Home.HomeSubCategory.Model.StockModel;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.NewStockAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.List;
import java.util.Objects;

public class StockList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_list);

        ImageButton backBtn = findViewById(R.id.backBtn);

        RecyclerView recyclerView = findViewById(R.id.stockListRecycleView);
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

        backBtn.setOnClickListener(v-> finish());
        newStockAdapter.setOnClickListener(new NewStockAdapter.OnItemClickListener() {
            @Override
            public void OnClick(StockModel stockModel, int position) {
                Intent intent = getIntent();
                if (intent != null){
                    String tag = intent.getStringExtra("TAG");
                    if (Objects.equals(tag, "STOCK-IN")) {
                        Intent intent1 = new Intent(StockList.this, StockInSub.class);
                        intent1.putExtra("NAME", stockModel.getProductName());
                        startActivity(intent1);
                        finish();
                    } else if (Objects.equals(tag, "STOCK-OUT")) {
                        Intent intent1 = new Intent(StockList.this, StockOut.class);
                        intent1.putExtra("NAME", stockModel.getProductName());
                        startActivity(intent1);
                        finish();
                    }
                }

            }


        });
        SearchView searchView = findViewById(R.id.stockListSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LiveData<List<StockModel>> filterStock= mainViewModel.searchStock("%" + newText + "%");
                filterStock.observe(StockList.this, new Observer<List<StockModel>>() {
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
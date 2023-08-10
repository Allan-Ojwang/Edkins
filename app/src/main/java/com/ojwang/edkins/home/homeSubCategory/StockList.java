package com.ojwang.edkins.home.homeSubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.ojwang.edkins.home.homeSubCategory.model.StockModel;
import com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter.NewStockAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.viewModel.MainViewModel;

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
                    String date = intent.getStringExtra("DATE");
                    if (Objects.equals(tag, "STOCK-IN")) {
                        Intent intent1 = new Intent(StockList.this, StockIn.class);
                        intent1.putExtra("PRODUCT-NAME", stockModel.getProductName());
                        intent1.putExtra("PRODUCT-ID", stockModel.getStockId());
                        intent1.putExtra("DATE", date);
                        startActivity(intent1);
                        finish();
                    } else if (Objects.equals(tag, "STOCK-OUT")) {
                        Intent intent1 = new Intent(StockList.this, StockOut.class);
                        intent1.putExtra("PRODUCT-NAME", stockModel.getProductName());
                        intent1.putExtra("PRODUCT-ID", stockModel.getStockId());
                        intent1.putExtra("DATE", date);
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
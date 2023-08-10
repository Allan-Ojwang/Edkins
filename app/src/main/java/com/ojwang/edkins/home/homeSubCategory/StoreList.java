package com.ojwang.edkins.home.homeSubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.ojwang.edkins.home.homeSubCategory.model.StoreModel;
import com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter.NewStoreAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.viewModel.MainViewModel;

import java.util.List;
import java.util.Objects;

public class StoreList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

        ImageButton backBtn = findViewById(R.id.backBtn);

        RecyclerView recyclerView = findViewById(R.id.storeListRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        NewStoreAdapter newStoreAdapter = new NewStoreAdapter();
        recyclerView.setAdapter(newStoreAdapter);

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getStoreData().observe(this, new Observer<List<StoreModel>>() {
            @Override
            public void onChanged(List<StoreModel> storeModels) {
                newStoreAdapter.setStoreModels(storeModels);
            }
        });
        backBtn.setOnClickListener(v-> finish());
        newStoreAdapter.setOnClickListener(new NewStoreAdapter.OnItemClickListener() {
            @Override
            public void OnClick(StoreModel storeModel, int position) {
                Intent intent = getIntent();
                if (intent != null){
                    String tag = intent.getStringExtra("TAG");
                    String date = intent.getStringExtra("DATE");
                    int pId = intent.getIntExtra("PRODUCT-ID",0);
                    String pName = intent.getStringExtra("PRODUCT-NAME");
                    if (Objects.equals(tag, "STOCK-IN")) {
                        Intent intent1 = new Intent(StoreList.this, StockIn.class);
                        intent1.putExtra("STORE-NAME", storeModel.getStoreName());
                        intent1.putExtra("STORE-ID", storeModel.getStoreId());
                        intent1.putExtra("DATE", date);
                        intent1.putExtra("PRODUCT-NAME", pName);
                        intent1.putExtra("PRODUCT-ID",pId);
                        startActivity(intent1);
                        finish();
                    } else if (Objects.equals(tag, "STOCK-OUT")) {
                        Intent intent1 = new Intent(StoreList.this, StockOut.class);
                        intent1.putExtra("STORE-NAME", storeModel.getStoreName());
                        intent1.putExtra("STORE-ID", storeModel.getStoreId());
                        intent1.putExtra("DATE", date);
                        intent1.putExtra("PRODUCT-NAME", pName);
                        intent1.putExtra("PRODUCT-ID",pId);
                        startActivity(intent1);
                        finish();
                    }
                }

            }
        });
    }
}
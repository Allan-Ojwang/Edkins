package com.ojwang.edkins.home.homeSubCategory;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ojwang.edkins.home.homeSubCategory.model.StoreModel;
import com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter.NewStoreAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.viewModel.MainViewModel;

import java.util.List;

public class Store extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        FloatingActionButton addFloatingActionButton = findViewById(R.id.addfloatingActionButton);
        ImageButton backBtn = findViewById(R.id.backBtn);

        RecyclerView recyclerView = findViewById(R.id.storeRecycleView);
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

        newStoreAdapter.setOnClickListener(new NewStoreAdapter.OnItemClickListener() {
            @Override
            public void OnClick(StoreModel storeModel, int position) {
                NewStoreTask newStoreTask = new NewStoreTask();
                Bundle bundle = new Bundle();
                bundle.putInt("ID",storeModel.getStoreId());
                bundle.putString("STORE-NAME",storeModel.getStoreName());
                bundle.putInt("ADAPTERPOS",position);
                newStoreTask.setArguments(bundle);
                newStoreTask.show(getSupportFragmentManager(),NewStoreTask.EDIT_TAG);

            }
        });

        backBtn.setOnClickListener(v -> finish());
        addFloatingActionButton.setOnClickListener(v -> {
            NewStoreTask newStoreTask = new NewStoreTask();
            newStoreTask.show(getSupportFragmentManager(),NewStoreTask.EDIT_TAG);
        });

    }
}
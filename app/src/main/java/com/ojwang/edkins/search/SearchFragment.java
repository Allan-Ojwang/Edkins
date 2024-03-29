package com.ojwang.edkins.search;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.ojwang.edkins.MainActivity;
import com.ojwang.edkins.R;
import com.ojwang.edkins.SplashScreen;
import com.ojwang.edkins.home.homeSubCategory.model.StockModel;
import com.ojwang.edkins.home.homeSubCategory.model.StockWithQuantityModel;
import com.ojwang.edkins.search.recyclerviewAdapter.SearchAdapter;
import com.ojwang.edkins.viewModel.MainViewModel;

import java.util.List;

public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.searchStockRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        SearchAdapter searchAdapter = new SearchAdapter();
        recyclerView.setAdapter(searchAdapter);

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getStockWithQuantityData().observe(getViewLifecycleOwner(), new Observer<List<StockWithQuantityModel>>() {
            @Override
            public void onChanged(List<StockWithQuantityModel> stockWithQuantityModels) {
                searchAdapter.setStockModels(stockWithQuantityModels);
            }
        });
//        mainViewModel.getStockData().observe(getViewLifecycleOwner(), new Observer<List<StockModel>>() {
//            @Override
//            public void onChanged(List<StockModel> stockModels) {
//                searchAdapter.setStockModels(stockModels);
//            }
//        });
    }
}
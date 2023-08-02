package com.ojwang.edkins.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ojwang.edkins.Home.HomeSubCategory.DebtTracker;
import com.ojwang.edkins.Home.HomeSubCategory.Paybills;
import com.ojwang.edkins.Home.HomeSubCategory.StockIn;
import com.ojwang.edkins.Home.HomeSubCategory.StockOut;
import com.ojwang.edkins.Home.HomeSubCategory.Store;
import com.ojwang.edkins.Home.HomeSubCategory.ToOrder;
import com.ojwang.edkins.Home.HomeSubCategory.Workers;
import com.ojwang.edkins.Home.HomeFragRecyclerviewModel.DashBtnModel;
import com.ojwang.edkins.Home.HomeFragRecyclerviewModel.DashBtnListener;
import com.ojwang.edkins.R;
import com.ojwang.edkins.Home.HomeFragRecyclerviewModel.dashAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements DashBtnListener {

    private ArrayList<DashBtnModel> btnArrayList;
    private String[] btnHeadings;
    private int[] imageResourseID;
    private RecyclerView recyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();
        recyclerview = view.findViewById(R.id.dashRecycleView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerview.setLayoutManager(gridLayoutManager);
        recyclerview.setHasFixedSize(true);
        dashAdapter dash_Adapter = new dashAdapter(getContext(),btnArrayList,this);
        recyclerview.setAdapter(dash_Adapter);
        dash_Adapter.notifyDataSetChanged();
    }

    private void dataInitialize() {
        btnArrayList = new ArrayList<>();
        btnHeadings = new String[]{
                getString(R.string.head_1),
                getString(R.string.head_2),
                getString(R.string.head_3),
                getString(R.string.head_4),
                getString(R.string.head_5),
                getString(R.string.head_6),
                getString(R.string.stores),
        };
        imageResourseID = new int[]{
                R.drawable.to_order,
                R.drawable.paybills,
                R.drawable.workers,
                R.drawable.debt_tracker,
                R.drawable.stock_out,
                R.drawable.stock_in,
                R.drawable.store,
        };
        for (int i =0; i<btnHeadings.length;i++){
            DashBtnModel dashBtnModel = new DashBtnModel(btnHeadings[i],imageResourseID[i]);
            btnArrayList.add(dashBtnModel);
        }
    }

    @Override
    public void onItemClick(int position) {

        switch (position){
            case 0:
                Intent to_order_intent = new Intent(getContext(), ToOrder.class);
                startActivity(to_order_intent);
                break;
            case 1:
                Intent paybills_intent = new Intent(getContext(), Paybills.class);
                startActivity(paybills_intent);
                break;
            case 2:
                Intent workers_intent = new Intent(getContext(), Workers.class);
                startActivity(workers_intent);
                break;
            case 3:
                Intent debt_intent = new Intent(getContext(), DebtTracker.class);
                startActivity(debt_intent);
                break;
            case 4:
                Intent stock_out_intent = new Intent(getContext(), StockOut.class);
                startActivity(stock_out_intent);
                break;
            case 5:
                Intent stock_in_intent = new Intent(getContext(), StockIn.class);
                startActivity(stock_in_intent);
                break;
            case 6:
                Intent store_intent = new Intent(getContext(), Store.class);
                startActivity(store_intent);
                break;
        }
    }
}
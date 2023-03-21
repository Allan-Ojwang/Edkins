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
import com.ojwang.edkins.Home.HomeSubCategory.ToOrder;
import com.ojwang.edkins.Home.HomeSubCategory.Workers;
import com.ojwang.edkins.Home.HomeFragRecyclerviewModel.DashBtnModel;
import com.ojwang.edkins.Home.HomeFragRecyclerviewModel.DashBtnListener;
import com.ojwang.edkins.R;
import com.ojwang.edkins.Home.HomeFragRecyclerviewModel.dashAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements DashBtnListener {

//     TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<DashBtnModel> btnArrayList;
    private String[] btnHeadings;
    private int[] imageResourseID;
    private RecyclerView recyclerview;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

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
        };
        imageResourseID = new int[]{
                R.drawable.to_order,
                R.drawable.paybills,
                R.drawable.workers,
                R.drawable.debt_tracker,
                R.drawable.stock_out,
                R.drawable.stock_in,
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
        }
    }
}
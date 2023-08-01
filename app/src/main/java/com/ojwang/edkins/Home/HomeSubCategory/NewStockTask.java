package com.ojwang.edkins.Home.HomeSubCategory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ojwang.edkins.R;

import java.util.Objects;

public class NewStockTask extends BottomSheetDialogFragment {
    public static final String EDIT_TAG = "PAYBILL_ADD_TASK_EDITED";
    private int adapterPos, id, sellingPrice;
    private String name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_stock_task, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            adapterPos = bundle.getInt("ADAPTERPOS");
            id = bundle.getInt("ID");
            name = bundle.getString("PRODUCT-NAME");
            sellingPrice = bundle.getInt("SELLING-PRICE");
        }

        return view;
    }
}

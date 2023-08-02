package com.ojwang.edkins.Home.HomeSubCategory;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.NewStockAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.Objects;

public class NewStoreTask extends BottomSheetDialogFragment {
    public static final String EDIT_TAG = "NEW_STORE_TASK_EDITED";
    private int adapterPos, id, sellingPrice;
    private String name;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_store_task, container, false);
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);


        ImageButton closeBtn = view.findViewById(R.id.closeBtn);
        EditText evStoreName = view.findViewById(R.id.evStoreName);
        TextView tvStoreTitle = view.findViewById(R.id.storeTitle);
        Button saveBtn = view.findViewById(R.id.saveBtn);

        NewStockAdapter newStockAdapter = new NewStockAdapter();
        Bundle bundle = getArguments();
        if (bundle != null) {
            adapterPos = bundle.getInt("ADAPTERPOS");
            id = bundle.getInt("ID");
            name = bundle.getString("PRODUCT-NAME");
            tvStoreTitle.setText("Edit Stores");

        }

        saveBtn.setOnClickListener(v->{

        });
        closeBtn.setOnClickListener(v -> Objects.requireNonNull(getDialog()).dismiss());

        return view;
    }
}

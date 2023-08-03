package com.ojwang.edkins.Home.HomeSubCategory;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ojwang.edkins.Home.HomeSubCategory.Model.StockModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.StoreModel;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.NewStockAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.Objects;

public class NewStoreTask extends BottomSheetDialogFragment {
    public static final String EDIT_TAG = "NEW_STORE_TASK_EDITED";
    private int adapterPos, sellingPrice;
    private int id = -1;
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

        Bundle bundle = getArguments();
        if (bundle != null) {
            adapterPos = bundle.getInt("ADAPTERPOS");
            id = bundle.getInt("ID");
            name = bundle.getString("STORE-NAME");
            tvStoreTitle.setText("Edit Store");
            evStoreName.setText(name);
        }

        saveBtn.setOnClickListener(v->{
            if (evStoreName.getText().toString().trim().isEmpty()){
                Toast.makeText(getContext(), "Please enter the store name", Toast.LENGTH_SHORT).show();
            } else{
                String name = evStoreName.getText().toString();
                Log.e("NAME",name);
                Log.e("ID", String.valueOf(id));

                StoreModel storeModel = new StoreModel(name);

                if (id != -1){
                    storeModel.setId(id);
                    mainViewModel.updateStore(storeModel);
                    Toast.makeText(getContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();
                    Objects.requireNonNull(getDialog()).dismiss();
                } else{
                    mainViewModel.insertStore(storeModel);
                    Toast.makeText(getContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
                    Objects.requireNonNull(getDialog()).dismiss();
                }
            }
        });
        closeBtn.setOnClickListener(v -> Objects.requireNonNull(getDialog()).dismiss());

        return view;
    }
}

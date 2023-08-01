package com.ojwang.edkins.Home.HomeSubCategory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ojwang.edkins.Home.ConfirmAlertDialog;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.NewStockAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.Objects;

public class NewStockTask extends BottomSheetDialogFragment {
    public static final String EDIT_TAG = "PAYBILL_ADD_TASK_EDITED";
    private int adapterPos, id, sellingPrice;
    private String name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_stock_task, container, false);
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);


        ImageButton editBtn = view.findViewById(R.id.editBtn);
        ImageButton deleteBtn = view.findViewById(R.id.deleteBtn);
        ImageButton closeBtn = view.findViewById(R.id.closeBtn);
        TextView productName = view.findViewById(R.id.productName);
        NewStockAdapter newStockAdapter = new NewStockAdapter();
        Bundle bundle = getArguments();
        if (bundle != null) {
            adapterPos = bundle.getInt("ADAPTERPOS");
            id = bundle.getInt("ID");
            name = bundle.getString("PRODUCT-NAME");
            sellingPrice = bundle.getInt("SELLING-PRICE");
            productName.setText(name);
        }
        editBtn.setOnClickListener(v->{
            Objects.requireNonNull(getDialog()).dismiss();
            sendDataToNewStockSub();
        });
        deleteBtn.setOnClickListener( v ->{
            ConfirmAlertDialog confirmAlertDialog = new ConfirmAlertDialog(getContext(), "Are you sure you want to delete this product?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            //Delete
                            if (id != -1){
                                mainViewModel.deleteStock(newStockAdapter.getStockAt(adapterPos));
                                Objects.requireNonNull(getDialog()).dismiss();
                                Toast.makeText(getContext(), "Product deleted successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Please select a product to delete", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            //Do nothing
                            break;
                    }

                }
            });
            confirmAlertDialog.showDialog();
        });
        closeBtn.setOnClickListener(v -> Objects.requireNonNull(getDialog()).dismiss());

        return view;
    }
    private void sendDataToNewStockSub() {
        Intent intent = new Intent(getActivity(), NewStockSub.class);
        intent.putExtra("NAME", name);
        intent.putExtra("SELLING-PRICE", sellingPrice);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}

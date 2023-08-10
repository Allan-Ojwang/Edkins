package com.ojwang.edkins.home.homeSubCategory;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.ojwang.edkins.home.ConfirmAlertDialog;
import com.ojwang.edkins.home.homeSubCategory.model.StoreModel;
import com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter.NewStoreAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.viewModel.MainViewModel;

import java.util.Objects;

public class NewStoreTask extends BottomSheetDialogFragment {
    public static final String EDIT_TAG = "NEW_STORE_TASK_EDITED";
    private int adapterPos;
    private int id = -1;
    private String name;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_store_task, container, false);
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        ImageButton closeBtn = view.findViewById(R.id.closeBtn);
        ImageButton deleteBtn = view.findViewById(R.id.deleteBtn);
        EditText evStoreName = view.findViewById(R.id.evStoreName);
        TextView tvStoreTitle = view.findViewById(R.id.storeTitle);
        Button saveBtn = view.findViewById(R.id.saveBtn);

        NewStoreAdapter newStoreAdapter = new NewStoreAdapter();

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

                StoreModel storeModel = new StoreModel(name);

                if (id != -1){
                    storeModel.setStoreId(id);
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

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmAlertDialog confirmAlertDialog = new ConfirmAlertDialog(getContext(), "Are you sure you want to delete this store?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Delete
                                if (id != -1){
                                    mainViewModel.deleteStore(newStoreAdapter.getStoreAt(adapterPos));
                                    Objects.requireNonNull(getDialog()).dismiss();
                                } else {
                                    Toast.makeText(getContext(), "Please select a store to delete", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //Do nothing
                                break;
                        }

                    }
                });
                confirmAlertDialog.showDialog();
            }
        });

        closeBtn.setOnClickListener(v -> Objects.requireNonNull(getDialog()).dismiss());

        return view;
    }
}

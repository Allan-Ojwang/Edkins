package com.ojwang.edkins.Home.HomeSubCategory;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ojwang.edkins.Home.ConfirmAlertDialog;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.ToOrderListAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.Objects;

public class AddOrderListTask extends BottomSheetDialogFragment {
    public static final String TAG = "ORDER_ADD_TASK";
    public static final String EDIT_TAG = "ORDER_ADD_TASK_EDITED";
    private int id = -1;
    private int adapterPos;

    public interface OnOrderListInputListener {
        void sendInput(String item, int quantity);
        void sendUpdateInput(int id, String item, int quantity);
    }
    public OnOrderListInputListener onOrderListInputListener;
    public EditText evItem, evQuantity;
    public Button save,delete;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String item;
        int quantity;
        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        View view = inflater.inflate(R.layout.add_order_list_task,container,false);
        evItem = view.findViewById(R.id.item);
        evQuantity = view.findViewById(R.id.quantity);
        save = view.findViewById(R.id.saveBtn);
        delete = view.findViewById(R.id.deleteBtn);

        ToOrderListAdapter toOrderListAdapter = new ToOrderListAdapter();

        Bundle bundle = getArguments();
        if (bundle != null) {
            adapterPos = bundle.getInt("ADAPTERPOS");
            id = bundle.getInt("ID");
            item = bundle.getString("ITEM");
            quantity = bundle.getInt("QUANTITY");
            evItem.setText(item);
            evQuantity.setText(String.valueOf(quantity));
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (evItem.getText().toString().trim().isEmpty() || evQuantity.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Please enter the item and quantity", Toast.LENGTH_SHORT).show();
                } else {
                    String item = evItem.getText().toString();
                    int quantity = Integer.parseInt(evQuantity.getText().toString());
                    if (id != -1){
                        onOrderListInputListener.sendUpdateInput(id,item,quantity);
                        Objects.requireNonNull(getDialog()).dismiss();
                    } else {
                        onOrderListInputListener.sendInput(item,quantity);
                        Objects.requireNonNull(getDialog()).dismiss();
                    }

                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmAlertDialog confirmAlertDialog = new ConfirmAlertDialog(getContext(), "Are you sure you want to delete?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Delete
                                if (id != -1){
                                    mainViewModel.deleteToOrderList(toOrderListAdapter.getOrderListAt(adapterPos));
                                    Objects.requireNonNull(getDialog()).dismiss();
                                } else {
                                    Toast.makeText(getContext(), "Please select a order to delete", Toast.LENGTH_SHORT).show();
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

        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onOrderListInputListener = (OnOrderListInputListener    ) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }
}

package com.ojwang.edkins.Home.HomeSubCategory;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ojwang.edkins.Home.ConfirmAlertDialog;
import com.ojwang.edkins.Home.HomeSubCategory.Model.CreditorModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.DebtorModel;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.CreditorAdapter;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.DebtorAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.List;
import java.util.Locale;

public class CreditorFragment extends Fragment  {

    private MainViewModel mainViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_creditor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton addBtn = view.findViewById(R.id.addfloatingActionButton);
        TextView totalAmount = view.findViewById(R.id.totalAmount);

        RecyclerView recyclerView = view.findViewById(R.id.creditorRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        CreditorAdapter creditorAdapter = new CreditorAdapter();
        recyclerView.setAdapter(creditorAdapter);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getCreditorData().observe(getViewLifecycleOwner(), new Observer<List<CreditorModel>>() {
            @Override
            public void onChanged(List<CreditorModel> creditorModels) {
                creditorAdapter.setCreditorModels(creditorModels);
            }
        });
        mainViewModel.getCreditorTotal().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float total) {
                String formattedTotal = "KSH " + String.format(Locale.getDefault(),"%.2f",total);
                totalAmount.setText(formattedTotal);
            }
        });
        addBtn.setOnClickListener(v -> {
            AddCreditorTask addCreditorTask = new AddCreditorTask();

            addCreditorTask.show(getParentFragmentManager(),AddCreditorTask.TAG);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                CreditorModel creditorModel = creditorAdapter.getCreditorAt(position);
                boolean newPStatus = !creditorModel.ispStatus(); // toggle the pStatus value
                creditorModel.setpStatus(newPStatus);
                mainViewModel.updateCreditor(creditorModel);

            }
        }).attachToRecyclerView(recyclerView);

        creditorAdapter.setOnLongClickListener(new CreditorAdapter.OnItemClickListener() {
            @Override
            public void OnLongClick(CreditorModel creditorModel, View view1) {
                final int position = recyclerView.getChildAdapterPosition(view1);
                ConfirmAlertDialog confirmAlertDialog = new ConfirmAlertDialog(getActivity(), "Are you sure you want to delete?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                mainViewModel.deleteCreditor(creditorAdapter.getCreditorAt(position));
                                //Delete
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

    }

}
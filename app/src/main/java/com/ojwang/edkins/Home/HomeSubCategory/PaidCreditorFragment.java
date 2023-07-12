package com.ojwang.edkins.Home.HomeSubCategory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ojwang.edkins.Home.HomeSubCategory.Model.CreditorModel;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.CreditorAdapter;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.PaidCreditorAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.List;
import java.util.Locale;

public class PaidCreditorFragment extends Fragment  {

    private MainViewModel mainViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.paid_fragment_creditor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.PaidCreditorRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        PaidCreditorAdapter paidCreditorAdapter = new PaidCreditorAdapter();
        recyclerView.setAdapter(paidCreditorAdapter);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getPaidCreditorData().observe(getViewLifecycleOwner(), new Observer<List<CreditorModel>>() {
            @Override
            public void onChanged(List<CreditorModel> creditorModels) {
                paidCreditorAdapter.setCreditorModels(creditorModels);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                CreditorModel creditorModel = paidCreditorAdapter.getCreditorAt(position);
                boolean newPStatus = !creditorModel.ispStatus(); // toggle the pStatus value
                creditorModel.setpStatus(newPStatus);
                mainViewModel.updateCreditor(creditorModel);

            }
        }).attachToRecyclerView(recyclerView);

        paidCreditorAdapter.setOnClickListener(new PaidCreditorAdapter.OnItemClickListener() {
            @Override
            public void OnClick(CreditorModel creditorModel, int position) {
                AddCreditorTask addCreditorTask = new AddCreditorTask();
                Bundle bundle = new Bundle();
                bundle.putInt("ID",creditorModel.getId());
                bundle.putString("NAME",creditorModel.getName());
                bundle.putString("REASON",creditorModel.getReason());
                bundle.putInt("AMOUNT",creditorModel.getAmount());
                bundle.putBoolean("STATUS",creditorModel.ispStatus());
                bundle.putInt("ADAPTERPOS",position);
                addCreditorTask.setArguments(bundle);
                addCreditorTask.show(getParentFragmentManager(),AddCreditorTask.EDIT_TAG);
            }
        });

    }

}
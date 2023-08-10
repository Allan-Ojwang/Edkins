package com.ojwang.edkins.home.homeSubCategory;

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
import com.ojwang.edkins.home.homeSubCategory.model.DebtorModel;
import com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter.DebtorAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.viewModel.MainViewModel;

import java.util.List;
import java.util.Locale;

public class DebtorFragment extends Fragment  {

    public MainViewModel mainViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_debtor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton addBtn = view.findViewById(R.id.addfloatingActionButton);
        TextView totalAmount = view.findViewById(R.id.total_Amount);

        RecyclerView recyclerView = view.findViewById(R.id.debtorRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        DebtorAdapter debtorAdapter = new DebtorAdapter();
        recyclerView.setAdapter(debtorAdapter);


        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getDebtorData().observe(getViewLifecycleOwner(), new Observer<List<DebtorModel>>() {
            @Override
            public void onChanged(List<DebtorModel> debtorModels) {
                debtorAdapter.setDebtorModels(debtorModels);

            }
        });

        mainViewModel.getDebtorTotal().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float total) {
                String formattedTotal = "KSH " + String.format(Locale.getDefault(),"%.2f",total);
                totalAmount.setText(formattedTotal);
            }
        });
        addBtn.setOnClickListener(v -> {
            AddDebtorTask addDebtorTask = new AddDebtorTask();
            addDebtorTask.show(getParentFragmentManager(),AddDebtorTask.TAG);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                DebtorModel debtorModel = debtorAdapter.getDebtorAt(position);
                boolean newPStatus = !debtorModel.ispStatus(); // toggle the pStatus value
                debtorModel.setpStatus(newPStatus);
                mainViewModel.updateDebtor(debtorModel);

            }
        }).attachToRecyclerView(recyclerView);


        debtorAdapter.setOnClickListener(new DebtorAdapter.OnItemClickListener() {
            @Override
            public void OnClick(DebtorModel debtorModel, int position) {
                AddDebtorTask addDebtorTask = new AddDebtorTask();
                Bundle bundle = new Bundle();
                bundle.putInt("ID",debtorModel.getId());
                bundle.putString("NAME",debtorModel.getName());
                bundle.putString("REASON",debtorModel.getReason());
                bundle.putInt("AMOUNT",debtorModel.getAmount());
                bundle.putBoolean("STATUS",debtorModel.ispStatus());
                bundle.putInt("ADAPTERPOS",position);
                addDebtorTask.setArguments(bundle);
                addDebtorTask.show(getParentFragmentManager(),AddDebtorTask.EDIT_TAG);
            }
        });



    }
}
package com.ojwang.edkins.home.homeSubCategory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ojwang.edkins.home.homeSubCategory.model.DebtorModel;
import com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter.PaidDebtorAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.viewModel.MainViewModel;

import java.util.List;

public class PaidDebtorFragment extends Fragment  {

    private MainViewModel mainViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.paid_fragment_debtor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.PaidDebtorRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        PaidDebtorAdapter paidDebtorAdapter = new PaidDebtorAdapter();
        recyclerView.setAdapter(paidDebtorAdapter);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getPaidDebtorData().observe(getViewLifecycleOwner(), new Observer<List<DebtorModel>>() {
            @Override
            public void onChanged(List<DebtorModel> debtorModels) {
                paidDebtorAdapter.setDebtorModels(debtorModels);
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
                DebtorModel debtorModel = paidDebtorAdapter.getDebtorAt(position);
                boolean newPStatus = !debtorModel.ispStatus(); // toggle the pStatus value
                debtorModel.setpStatus(newPStatus);
                mainViewModel.updateDebtor(debtorModel);

            }
        }).attachToRecyclerView(recyclerView);
        paidDebtorAdapter.setOnClickListener(new PaidDebtorAdapter.OnItemClickListener() {
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
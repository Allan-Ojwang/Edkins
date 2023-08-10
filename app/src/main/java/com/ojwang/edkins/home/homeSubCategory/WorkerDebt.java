package com.ojwang.edkins.home.homeSubCategory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ojwang.edkins.home.ConfirmAlertDialog;
import com.ojwang.edkins.home.homeSubCategory.model.WorkerDebtModel;
import com.ojwang.edkins.home.homeSubCategory.model.WorkerModel;
import com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter.WorkerAdapter;
import com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter.WorkerDebtAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.viewModel.MainViewModel;

import java.util.Locale;

public class WorkerDebt extends AppCompatActivity implements AddWorkerDebtTask.OnWorkerDebtInputListener,    AddWorkerTask.OnWorkerInputListener{

    private MainViewModel mainViewModel;
    private int workerId,adapPos,idNo,number;
    private String workerName;

    private Float totalLoans, totalSavings;

    @Override
    public void sendInput(String date, int amount, String dStatus) {
        WorkerDebtModel workerDebtModel = new WorkerDebtModel(date, dStatus, amount,workerId);
        mainViewModel.insertWorkerDebt(workerDebtModel);
    }

    @Override
    public void sendUpdateInput(int id, String date, int amount, String dStatus) {
        WorkerDebtModel workerDebtModel = new WorkerDebtModel(date,dStatus, amount,workerId);
        workerDebtModel.setDebtId(id);
        mainViewModel.updateWorkerDebt(workerDebtModel);
    }

    @Override
    public void sendInput(String name, int id_number, int number) {

    }

    @Override
    public void sendUpdateInput(int id, String name, int id_number, int number) {
        WorkerModel workerModel = new WorkerModel(name,number,id_number);
        workerModel.setWorkerId(id);
        mainViewModel.updateWorker(workerModel);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_debt);

        ImageButton backBtn = findViewById(R.id.backBtn);
        ImageButton addBtn = findViewById(R.id.addBtn);
        ImageButton deleteBtn = findViewById(R.id.deleteBtn);
        ImageButton updateBtn = findViewById(R.id.updateBtn);
        TextView totalAmount = findViewById(R.id.totalAmount);


        Intent intent = getIntent();
        workerName = intent.getStringExtra("workerName");
        adapPos = intent.getIntExtra("adapPos",0);
        workerId = intent.getIntExtra("workerId",1);
        idNo = intent.getIntExtra("IDNO",0);
        number = intent.getIntExtra("NUMBER",0);

        TextView workerNameTv = findViewById(R.id.workerName);
        workerNameTv.setText(workerName);

        RecyclerView recyclerView = findViewById(R.id.workerDebtRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        WorkerDebtAdapter workerDebtAdapter = new WorkerDebtAdapter();
        WorkerAdapter workerAdapter = new WorkerAdapter();
        recyclerView.setAdapter(workerDebtAdapter);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getWorkerDebtData(workerId).observe(this, workerDebtAdapter::setWorkerDebtNotes);


        MediatorLiveData<Pair<Float, Float>> mediatorLiveData = new MediatorLiveData<>();
        LiveData<Float> totalLoan = mainViewModel.getLoanTotal();
        mediatorLiveData.addSource(totalLoan, loans ->{
            Pair<Float, Float> pair = mediatorLiveData.getValue();
            if (pair == null) {
                pair = new Pair<>(loans, null);
            } else {
                pair = new Pair<>(loans, pair.second);
            }
            mediatorLiveData.setValue(pair);
        });

        LiveData<Float> totalSaving = mainViewModel.getSavingTotal();
        mediatorLiveData.addSource(totalSaving, saving ->{
            Pair<Float, Float> pair = mediatorLiveData.getValue();
            if (pair == null) {
                pair = new Pair<>(saving, null);
            } else {
                pair = new Pair<>(pair.first, saving);
            }
            mediatorLiveData.setValue(pair);
        });

        mediatorLiveData.observe(this, floatFloatPair -> {
            totalSavings = floatFloatPair.first;
            totalLoans = floatFloatPair.second;

            float totalDebt ;
            if (totalSavings != null && totalLoans != null) {
                totalDebt = totalSavings - totalLoans;
                String formattedTotal = "KSH " + String.format(Locale.getDefault(),"%.2f",totalDebt);
                totalAmount.setText(formattedTotal);
            }
        });
        backBtn.setOnClickListener(v -> finish());
        addBtn.setOnClickListener(v -> {
            AddWorkerDebtTask addWorkerDebtTask = new AddWorkerDebtTask();
            addWorkerDebtTask.show(getSupportFragmentManager(),AddWorkerDebtTask.TAG);
        });

        workerDebtAdapter.setOnClickListener((workerDebtModel, position) -> {
            AddWorkerDebtTask addWorkerDebtTask = new AddWorkerDebtTask();
            Bundle bundle = new Bundle();
            bundle.putInt("ID",workerDebtModel.getDebtId());
            bundle.putString("DATE",workerDebtModel.getDate());
            bundle.putString("DSTATUS",workerDebtModel.getDStatus());
            bundle.putInt("AMOUNT",workerDebtModel.getAmount());
            bundle.putInt("ADAPTERPOS",position);
            addWorkerDebtTask.setArguments(bundle);
            addWorkerDebtTask.show(getSupportFragmentManager(),AddWorkerDebtTask.EDIT_TAG);
        });

        deleteBtn.setOnClickListener(v -> {
            ConfirmAlertDialog confirmAlertDialog = new ConfirmAlertDialog(WorkerDebt.this, "Are you sure you want to delete the worker's record? \n \n THIS PROCESS IS IRREVERSIBLE!!!", (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Delete
                        if (workerId != -1){
                            mainViewModel.deleteWorker(workerAdapter.getWorkerAt(adapPos));
                            dialog.dismiss();
                            finish();
                        } else {
                            Toast.makeText(WorkerDebt.this, "Please select a debt to delete", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //Do nothing
                        break;
                }

            });
            confirmAlertDialog.showDialog();
        });

        updateBtn.setOnClickListener(v -> {
            AddWorkerTask addWorkerTask = new AddWorkerTask();
            Bundle bundle = new Bundle();
            bundle.putInt("ID",workerId);
            bundle.putString("NAME",workerName);
            bundle.putInt("IdNUMBER",idNo);
            bundle.putInt("NUMBER",number);
            addWorkerTask.setArguments(bundle);
            addWorkerTask.show(getSupportFragmentManager(),AddWorkerTask.EDIT_TAG);
        });
    }

}
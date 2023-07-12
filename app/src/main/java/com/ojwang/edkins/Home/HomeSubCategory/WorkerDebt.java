package com.ojwang.edkins.Home.HomeSubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ojwang.edkins.Home.ConfirmAlertDialog;
import com.ojwang.edkins.Home.HomeSubCategory.Model.WorkerDebtModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.WorkerModel;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.WorkerAdapter;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.WorkerDebtAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

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

        mainViewModel.getWorkerDebtData(workerId).observe(this, new Observer<List<WorkerDebtModel>>() {
            @Override
            public void onChanged(List<WorkerDebtModel> workerDebtModels) {
                workerDebtAdapter.setWorkerDebtNotes(workerDebtModels);
                float totalDebt ;
                if (totalSavings != null && totalLoans != null) {
                    totalDebt = totalSavings - totalLoans;
                    String formattedTotal = "KSH " + String.format(Locale.getDefault(),"%.2f",totalDebt);
                    totalAmount.setText(formattedTotal);
                }
            }
        });

        mainViewModel.getLoanTotal().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float loans) {
                if (loans != null) {
                    totalLoans = loans;
                }
            }
        });

        mainViewModel.getSavingTotal().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float savings) {
                if (savings != null) {
                    totalSavings = savings;
                }
            }
        });

        backBtn.setOnClickListener(v -> finish());
        addBtn.setOnClickListener(v -> {
            AddWorkerDebtTask addWorkerDebtTask = new AddWorkerDebtTask();
            addWorkerDebtTask.show(getSupportFragmentManager(),AddWorkerDebtTask.TAG);
        });

        workerDebtAdapter.setOnClickListener(new WorkerDebtAdapter.OnItemClickListener() {
            @Override
            public void OnClick(WorkerDebtModel workerDebtModel, int position) {
                AddWorkerDebtTask addWorkerDebtTask = new AddWorkerDebtTask();
                Bundle bundle = new Bundle();
                bundle.putInt("ID",workerDebtModel.getDebtId());
                bundle.putString("DATE",workerDebtModel.getDate());
                bundle.putString("DSTATUS",workerDebtModel.getDStatus());
                bundle.putInt("AMOUNT",workerDebtModel.getAmount());
                bundle.putInt("ADAPTERPOS",position);
                addWorkerDebtTask.setArguments(bundle);
                addWorkerDebtTask.show(getSupportFragmentManager(),AddWorkerDebtTask.EDIT_TAG);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmAlertDialog confirmAlertDialog = new ConfirmAlertDialog(WorkerDebt.this, "Are you sure you want to delete the worker's record? \n \n THIS PROCESS IS IRREVERSIBLE!!!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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

                    }
                });
                confirmAlertDialog.showDialog();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddWorkerTask addWorkerTask = new AddWorkerTask();
                Bundle bundle = new Bundle();
                bundle.putInt("ID",workerId);
                bundle.putString("NAME",workerName);
                bundle.putInt("IdNUMBER",idNo);
                bundle.putInt("NUMBER",number);
                addWorkerTask.setArguments(bundle);
                addWorkerTask.show(getSupportFragmentManager(),AddWorkerTask.EDIT_TAG);
            }
        });
    }

}
package com.ojwang.edkins.home.homeSubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ojwang.edkins.home.homeSubCategory.model.WorkerModel;
import com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter.WorkerAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.viewModel.MainViewModel;

import java.util.List;

public class Workers extends AppCompatActivity implements AddWorkerTask.OnWorkerInputListener{

    public String workerName;
    public int workerId, IdNo, Number, adapPos;

    private MainViewModel mainViewModel;

    @Override
    public void sendInput(String name, int id_number, int number) {

        WorkerModel workerModel = new WorkerModel(name,number,id_number);
        mainViewModel.insertWorker(workerModel);

    }

    @Override
    public void sendUpdateInput(int id, String name, int id_number, int number) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers);

        FloatingActionButton addBtn = findViewById(R.id.addfloatingActionButton);
        ImageButton backBtn = findViewById(R.id.backBtn);

        RecyclerView recyclerView = findViewById(R.id.workerRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        WorkerAdapter workerAdapter = new WorkerAdapter();
        recyclerView.setAdapter(workerAdapter);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getWorkerData().observe(this, new Observer<List<WorkerModel>>() {
            @Override
            public void onChanged(List<WorkerModel> workerModels) {
                workerAdapter.setWorkerNotes(workerModels);
            }
        });

        addBtn.setOnClickListener(v -> {
            AddWorkerTask addWorkerTask = new AddWorkerTask();
            addWorkerTask.show(getSupportFragmentManager(),AddWorkerTask.TAG);
        });

        backBtn.setOnClickListener(v -> finish());

        workerAdapter.setOnClickListener(new WorkerAdapter.OnItemClickListener() {
            @Override
            public void OnClick(WorkerModel workerModel, int position) {
                workerName = workerModel.getWorker_name();
                adapPos = position;
                workerId = workerModel.getWorkerId();
                IdNo = workerModel.getId_number();
                Number = workerModel.getNumber();
                Intent intent = new Intent(Workers.this, WorkerDebt.class);
                intent.putExtra("workerId",workerId);
                intent.putExtra("workerName",workerName);
                intent.putExtra("IDNO",IdNo);
                intent.putExtra("NUMBER",Number);
                intent.putExtra("adapPos",adapPos);
                startActivity(intent);

            }
        });
    }

}
package com.ojwang.edkins.Home.HomeSubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ojwang.edkins.Home.HomeSubCategory.Model.WorkerModel;
import com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter.WorkerAdapter;
import com.ojwang.edkins.R;
import com.ojwang.edkins.ViewModel.MainViewModel;

import java.util.List;

public class Workers extends AppCompatActivity implements AddWorkerTask.OnWorkerInputListener{

    public String Name;
    public int Id, IdNo, Number;

    private MainViewModel mainViewModel;

    @Override
    public void sendInput(String name, int id_number, int number) {
        Name = name;
        IdNo = id_number;
        Number = number;
        WorkerModel workerModel = new WorkerModel(Name,Number,IdNo);
        mainViewModel.insertWorker(workerModel);

    }

    @Override
    public void sendUpdateInput(int id, String name, int id_number, int number) {
        Id = id;
        Name = name;
        IdNo = id_number;
        Number = number;
        WorkerModel workerModel = new WorkerModel(Name,Number,IdNo);
        workerModel.setWorkerId(Id);
        mainViewModel.updateWorker(workerModel);

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
            public void OnClick(WorkerModel workerModel) {
                AddWorkerTask addWorkerTask = new AddWorkerTask();
                Bundle bundle = new Bundle();
                bundle.putInt("ID",workerModel.getWorkerId());
                bundle.putString("NAME",workerModel.getWorker_name());
                bundle.putInt("NUMBER",workerModel.getNumber());
                bundle.putInt("IdNUMBER",workerModel.getId_number());
                addWorkerTask.setArguments(bundle);
                addWorkerTask.show(getSupportFragmentManager(),AddWorkerTask.EDIT_TAG);
            }
        });
    }

}
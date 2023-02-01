package com.ojwang.edkins.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ojwang.edkins.Home.HomeSubCategory.Model.PaybillModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.WorkerModel;
import com.ojwang.edkins.Repo.MainRepo;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private MainRepo mainRepo;
    private LiveData<List<PaybillModel>> paybillData;
    private  LiveData<List<WorkerModel>> workerData;
    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepo = new MainRepo(application);
        paybillData= mainRepo.getPaybillNotes();
        workerData= mainRepo.getWorkerNotes();
    }

    public void insertPaybill (PaybillModel paybillModel){
        mainRepo.insertPaybill(paybillModel);
    }
    public void updatePaybill (PaybillModel paybillModel){
        mainRepo.updatePaybill(paybillModel);
    }
    public void deletePaybill (PaybillModel paybillModel){
        mainRepo.deletePaybill(paybillModel);
    }
    public LiveData<List<PaybillModel>> getPaybillData(){
        return paybillData;
    }

    public void insertWorker (WorkerModel workerModel){
        mainRepo.insertWorker(workerModel);
    }
    public void updateWorker (WorkerModel workerModel){
        mainRepo.updateWorker(workerModel);
    }
    public void deleteWorker (WorkerModel workerModel){
        mainRepo.deleteWorker(workerModel);
    }
    public LiveData<List<WorkerModel>> getWorkerData(){
        return workerData;
    }
}

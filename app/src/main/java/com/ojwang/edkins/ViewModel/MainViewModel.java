package com.ojwang.edkins.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ojwang.edkins.Home.HomeSubCategory.Model.CreditorModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.DebtorModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.PaybillModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.WorkerModel;
import com.ojwang.edkins.Repo.MainRepo;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private final MainRepo mainRepo;
    private final LiveData<List<PaybillModel>> paybillData;
    private final LiveData<List<WorkerModel>> workerData;
    private final LiveData<List<CreditorModel>> creditorData;

    private final LiveData<List<DebtorModel>> debtorData;
    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepo = new MainRepo(application);
        paybillData= mainRepo.getPaybillNotes();
        workerData= mainRepo.getWorkerNotes();
        creditorData = mainRepo.getCreditorNotes();
        debtorData = mainRepo.getDebtorNotes();
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

    public void insertCreditor (CreditorModel creditorModel){
        mainRepo.insertCreditor(creditorModel);
    }
    public void updateCreditor (CreditorModel creditorModel){
        mainRepo.updateCreditor(creditorModel);
    }
    public void deleteCreditor (CreditorModel creditorModel){
        mainRepo.deleteCreditor(creditorModel);
    }
    public LiveData<List<CreditorModel>> getCreditorData() {
        return creditorData;
    }
    public LiveData<Float>getCreditorTotal(){return mainRepo.getCreditorTotal();}

    public void insertDebtor (DebtorModel debtorModel){
        mainRepo.insertDebtor(debtorModel);
    }
    public void updateDebtor (DebtorModel debtorModel){
        mainRepo.updateDebtor(debtorModel);
    }
    public void deleteDebtor (DebtorModel debtorModel){
        mainRepo.deleteDebtor(debtorModel);
    }
    public LiveData<List<DebtorModel>> getDebtorData() {
        return debtorData;
    }
    public LiveData<Float>getDebtorTotal(){return mainRepo.getDebtorTotal();}



}

package com.ojwang.edkins.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ojwang.edkins.Home.HomeSubCategory.Model.CreditorModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.DebtorModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.PaybillModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.StockModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.ToOrderListModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.ToOrderModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.WorkerDebtModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.WorkerModel;
import com.ojwang.edkins.Repo.MainRepo;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private final MainRepo mainRepo;
    private final LiveData<List<PaybillModel>> paybillData;
    private final LiveData<List<WorkerModel>> workerData;
    private final LiveData<List<CreditorModel>> creditorData;
    private final LiveData<List<CreditorModel>> paidCreditorData;
    private final LiveData<List<ToOrderModel>> toOrderData;
    private final LiveData<List<DebtorModel>> debtorData;
    private final LiveData<List<DebtorModel>> paidDebtorData;
    private final LiveData<List<StockModel>> stockData;
    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepo = new MainRepo(application);
        paybillData= mainRepo.getPaybillNotes();
        workerData= mainRepo.getWorkerNotes();
        creditorData = mainRepo.getCreditorNotes();
        paidCreditorData = mainRepo.getPaidCreditorNotes();
        debtorData = mainRepo.getDebtorNotes();
        paidDebtorData = mainRepo.getPaidDebtorNotes();
        toOrderData = mainRepo.getToOrderNotes();
        stockData = mainRepo.getStockData();
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
    public LiveData<List<PaybillModel>> searchPaybill(String query){
        return mainRepo.searchPaybill(query);
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

    public void insertWorkerDebt (WorkerDebtModel workerDebtModel){
        mainRepo.insertWorkerDebt(workerDebtModel);
    }
    public void updateWorkerDebt (WorkerDebtModel workerDebtModel){
        mainRepo.updateWorkerDebt(workerDebtModel);
    }
    public void deleteWorkerDebt (WorkerDebtModel workerDebtModel){
        mainRepo.deleteWorkerDebt(workerDebtModel);
    }

    public LiveData<List<WorkerDebtModel>> getWorkerDebtData(int workerId){
        return mainRepo.getWorkerDebtData(workerId);
    }

    public LiveData<Float>getLoanTotal(){return mainRepo.getLoanTotal();}

    public LiveData<Float>getSavingTotal(){return mainRepo.getSavingTotal();}

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
    public LiveData<List<CreditorModel>> getPaidCreditorData() {
        return paidCreditorData;
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
    public LiveData<List<DebtorModel>> getPaidDebtorData() {
        return paidDebtorData;
    }
    public LiveData<Float>getDebtorTotal(){return mainRepo.getDebtorTotal();}


    public LiveData<List<ToOrderModel>> getToOrderData(){return toOrderData;}
    public LiveData<Long> insertOrder(ToOrderModel order) {
        return mainRepo.insertOrders(order);
    }
    public void updateOrder(ToOrderModel order) {
        mainRepo.updateOrder(order);
    }
    public void deleteToOrder (ToOrderModel toOrderModel){mainRepo.deleteOrder(toOrderModel);}

    public void insertToOrderList (ToOrderListModel toOrderListModel){mainRepo.insertOrderList(toOrderListModel);}
    public void updateToOrderList (ToOrderListModel toOrderListModel){mainRepo.updateOrderList(toOrderListModel);}
    public void deleteToOrderList (ToOrderListModel toOrderListModel){mainRepo.deleteOrderList(toOrderListModel);}

    public LiveData<List<ToOrderListModel>> getToOrderListData(Long orderId){
        return mainRepo.getToOrderListData(orderId);
    }
    public LiveData<Integer> getNumbOfOrder(int orderId){
        return mainRepo.getNumbOfOrder(orderId);
    }
    public LiveData<Integer> getNumbOfOrderStatus(int orderId){
        return mainRepo.getNumbOfOrderStatus(orderId);
    }

    public LiveData<List<StockModel>> getStockData(){return stockData;}
    public void insertStock(StockModel stockModel) {
         mainRepo.insertStock(stockModel);
    }
    public void updateStock(StockModel stockModel) {
        mainRepo.updateStock(stockModel);
    }
    public void deleteToOrder (StockModel stockModel){mainRepo.deleteStock(stockModel);}
}

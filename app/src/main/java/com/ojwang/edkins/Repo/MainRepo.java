package com.ojwang.edkins.Repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ojwang.edkins.Database.EdkinsDb;
import com.ojwang.edkins.Home.HomeSubCategory.Dao.CreditorDao;
import com.ojwang.edkins.Home.HomeSubCategory.Dao.DebtorDao;
import com.ojwang.edkins.Home.HomeSubCategory.Dao.PaybillDao;
import com.ojwang.edkins.Home.HomeSubCategory.Dao.StockDao;
import com.ojwang.edkins.Home.HomeSubCategory.Dao.ToOrderDao;
import com.ojwang.edkins.Home.HomeSubCategory.Dao.WorkerDao;
import com.ojwang.edkins.Home.HomeSubCategory.Model.CreditorModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.DebtorModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.PaybillModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.StockModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.ToOrderListModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.ToOrderModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.WorkerDebtModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.WorkerModel;

import java.util.List;
import java.util.concurrent.FutureTask;

public class MainRepo {
    private final PaybillDao paybillDao;
    LiveData<List<PaybillModel>> paybillData;
    private final WorkerDao workerDao;
    LiveData<List<WorkerModel>> workerData;

    private final LiveData<Float> loanTotal;

    private final LiveData<Float> savingTotal;

    private final CreditorDao creditorDao;
    LiveData<List<CreditorModel>> creditorData;
    LiveData<List<CreditorModel>> paidCreditorData;
    private final LiveData<Float> creditorTotal;


    private final DebtorDao debtorDao;
    LiveData<List<DebtorModel>> debtorData;
    LiveData<List<DebtorModel>> paidDebtorData;
    private final LiveData<Float> debtorTotal;

    private final ToOrderDao toOrderDao;
    LiveData<List<ToOrderModel>> toOrderData;

    private final StockDao stockDao;
    LiveData<List<StockModel>> stockData;

    public MainRepo(Application application) {
        EdkinsDb db = EdkinsDb.getInstance(application);
        paybillDao = db.paybillDao();
        paybillData = paybillDao.getPaybillData();
        workerDao = db.workersDao();
        workerData = workerDao.getWorkerData();
        loanTotal = workerDao.getTotalLoan();
        savingTotal = workerDao.getTotalSaving();
        creditorDao = db.creditorDao();
        creditorData = creditorDao.getAllCreditorData();
        paidCreditorData = creditorDao.getPaidCreditorData();
        creditorTotal = creditorDao.getTotalAmount();
        debtorDao = db.debtorDao();
        debtorData = debtorDao.getAllDebtorData();
        paidDebtorData = debtorDao.getPaidDebtorData();
        debtorTotal = debtorDao.getTotalAmount();
        toOrderDao = db.toOrderDao();
        toOrderData = toOrderDao.getToOrderData();
        stockDao = db.stockDao();
        stockData = stockDao.getStockData();
    }

    //    PAYBILL NOTE
    public void insertPaybill(PaybillModel paybillModel) {
        new InsertPaybillFutureTask(paybillDao, paybillModel).execute();
    }

    public void updatePaybill(PaybillModel paybillModel) {
        new UpdatePaybillFutureTask(paybillDao,paybillModel).execute();
    }

    public void deletePaybill(PaybillModel paybillModel) {
        new DeletePaybillFutureTask(paybillDao,paybillModel).execute();
    }

    public LiveData<List<PaybillModel>> getPaybillNotes() {
        return paybillData;
    }

    public LiveData<List<PaybillModel>> searchPaybill(String query){
        return paybillDao.searchPaybill(query);
    }

    private static class InsertPaybillFutureTask extends FutureTask<Void> {

        private InsertPaybillFutureTask(PaybillDao paybillDao, PaybillModel paybillModel) {
            super(() -> {
                paybillDao.insertPaybill(paybillModel);
                return null;
            });
        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }

    private static class UpdatePaybillFutureTask extends FutureTask<Void> {

        private UpdatePaybillFutureTask(PaybillDao paybillDao, PaybillModel paybillModel) {
            super(() -> {
                paybillDao.updatePaybill(paybillModel);
                return null;
            });

        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }

    private static class DeletePaybillFutureTask extends FutureTask<Void> {

        private DeletePaybillFutureTask(PaybillDao paybillDao, PaybillModel paybillModel) {
            super(() -> {
                paybillDao.deletePaybill(paybillModel);
                return null;
            });

        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }


    //    WORKERS NOTE
    public void insertWorker(WorkerModel workerModel) {
        new InsertWorkerFutureTask(workerDao,workerModel).execute();
    }

    public void updateWorker(WorkerModel workerModel) {
        new UpdateWorkerFutureTask(workerDao,workerModel).execute();
    }

    public void deleteWorker(WorkerModel workerModel) {
        new DeleteWorkerFutureTask(workerDao,workerModel).execute();
    }

    public LiveData<List<WorkerModel>> getWorkerNotes() {
        return workerData;
    }


    private static class InsertWorkerFutureTask extends FutureTask<Void>{

        private InsertWorkerFutureTask(WorkerDao workerDao, WorkerModel workerModel) {
            super(() -> {
                workerDao.insertWorker(workerModel);
                return null;
            });

        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }

    private static class UpdateWorkerFutureTask extends FutureTask<Void>{

        private UpdateWorkerFutureTask(WorkerDao workerDao, WorkerModel workerModel) {
            super(() -> {
                workerDao.updateWorker(workerModel);
                return null;
            });

        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }

    private static class DeleteWorkerFutureTask extends FutureTask<Void>{

        private DeleteWorkerFutureTask(WorkerDao workerDao, WorkerModel workerModel) {
            super(() -> {
                workerDao.deleteWorker(workerModel);
                return null;
            });

        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }

    //    WORKERS DEBT NOTE
    public void insertWorkerDebt(WorkerDebtModel workerDebtModel) {
        new InsertWorkerDebtFutureTask(workerDao,workerDebtModel).execute();
    }

    public void updateWorkerDebt(WorkerDebtModel workerDebtModel) {
        new UpdateWorkerDebtFutureTask(workerDao,workerDebtModel).execute();
    }

    public void deleteWorkerDebt(WorkerDebtModel workerDebtModel) {
        new DeleteWorkerDebtFutureTask(workerDao, workerDebtModel).execute();
    }

    public LiveData<List<WorkerDebtModel>> getWorkerDebtData(int workerId) {
        return workerDao.getWorkerWithDebt(workerId);
    }

    public LiveData<Float> getLoanTotal() {
        return loanTotal;
    }

    public LiveData<Float> getSavingTotal() {
        return savingTotal;
    }
    
    private static class InsertWorkerDebtFutureTask extends FutureTask<Void>{

        private InsertWorkerDebtFutureTask(WorkerDao workerDao, WorkerDebtModel workerDebtModel) {
            super(() -> {
                workerDao.insertWorkerDebt(workerDebtModel);
                return null;
            });

        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }

    private static class UpdateWorkerDebtFutureTask extends FutureTask<Void>{

        private UpdateWorkerDebtFutureTask(WorkerDao workerDao, WorkerDebtModel workerDebtModel) {
            super(() -> {
                workerDao.updateWorkerDebt(workerDebtModel);
                return null;
            });

        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }

    private static class DeleteWorkerDebtFutureTask extends FutureTask<Void>{

        private DeleteWorkerDebtFutureTask(WorkerDao workerDao, WorkerDebtModel workerDebtModel) {
            super(() -> {
                workerDao.deleteWorkerDebt(workerDebtModel);
                return null;
            });

        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }


    //    CREDITORS NOTE
    public void insertCreditor(CreditorModel creditorModel) {
        new InsertCreditorFutureTask(creditorDao,creditorModel).execute();
    }

    public void updateCreditor(CreditorModel creditorModel) {
        new UpdateCreditorFutureTask(creditorDao,creditorModel).execute();
    }

    public void deleteCreditor(CreditorModel creditorModel) {
        new DeleteCreditorFutureTask(creditorDao,creditorModel).execute();
    }

    public LiveData<List<CreditorModel>> getCreditorNotes() {
        return creditorData;
    }
    public LiveData<List<CreditorModel>> getPaidCreditorNotes() {
        return paidCreditorData;
    }

    public LiveData<Float> getCreditorTotal() {
        return creditorTotal;
    }

    private static class InsertCreditorFutureTask extends FutureTask<Void> {

        private InsertCreditorFutureTask(CreditorDao creditorDao, CreditorModel creditorModel) {
            super(() -> {
                creditorDao.insertCreditor(creditorModel);
                return null;
            });
        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }

    private static class UpdateCreditorFutureTask extends FutureTask<Void> {

        private UpdateCreditorFutureTask(CreditorDao creditorDao, CreditorModel creditorModel) {
            super(() -> {
                creditorDao.updateCreditor(creditorModel);
                return null;
            });
        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }

    private static class DeleteCreditorFutureTask extends FutureTask<Void> {

        private DeleteCreditorFutureTask(CreditorDao creditorDao, CreditorModel creditorModel) {
            super(() -> {
                creditorDao.deleteCreditor(creditorModel);
                return null;
            });
        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }


    //    DEBTORS NOTE
    public void insertDebtor(DebtorModel debtorModel) {
        new InsertDebtorFutureTask(debtorDao,debtorModel).execute();
    }

    public void updateDebtor(DebtorModel debtorModel) {
        new UpdateDebtorFutureTask(debtorDao,debtorModel).execute();
    }

    public void deleteDebtor(DebtorModel debtorModel) {
        new DeleteDebtorFutureTask(debtorDao,debtorModel).execute();
    }

    public LiveData<List<DebtorModel>> getDebtorNotes() {
        return debtorData;
    }

    public LiveData<List<DebtorModel>> getPaidDebtorNotes() {
        return paidDebtorData;
    }

    public LiveData<Float> getDebtorTotal() {
        return debtorTotal;
    }

    private static class InsertDebtorFutureTask extends FutureTask<Void> {

        private InsertDebtorFutureTask(DebtorDao debtorDao, DebtorModel debtorModel) {
            super(() -> {
                debtorDao.insertDebtor(debtorModel);
                return null;
            });
        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }

    private static class UpdateDebtorFutureTask extends FutureTask<Void> {

        private UpdateDebtorFutureTask(DebtorDao debtorDao, DebtorModel debtorModel) {
            super(() -> {
                debtorDao.updateDebtor(debtorModel);
                return null;
            });
        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }

    private static class DeleteDebtorFutureTask extends FutureTask<Void> {

        private DeleteDebtorFutureTask(DebtorDao debtorDao, DebtorModel debtorModel) {
            super(() -> {
                debtorDao.deleteDebtor(debtorModel);
                return null;
            });
        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }

    //    To Order NOTE
    public LiveData<Long> insertOrders(ToOrderModel order) {
        MutableLiveData<Long> insertedOrderId = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            long orderId = toOrderDao.insertOrder(order);
            insertedOrderId.postValue(orderId);
        });
        return insertedOrderId;
    }
    public void updateOrder(ToOrderModel toOrderModel) {
        new UpdateToOrderFutureTask(toOrderDao,toOrderModel).execute();
    }
    public void deleteOrder(ToOrderModel toOrderModel) {
        new DeleteToOrderFutureTask(toOrderDao,toOrderModel).execute();
    }


    public LiveData<List<ToOrderModel>> getToOrderNotes() {
        return toOrderData;
    }

    private static class UpdateToOrderFutureTask extends FutureTask<Void> {

        private UpdateToOrderFutureTask(ToOrderDao toOrderDao, ToOrderModel toOrderModel) {
            super(() -> {
                toOrderDao.updateOrder(toOrderModel);
                return null;
            });
        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }

    private static class DeleteToOrderFutureTask extends FutureTask<Void> {

        private DeleteToOrderFutureTask(ToOrderDao toOrderDao, ToOrderModel toOrderModel) {
            super(() -> {
                toOrderDao.deleteOrder(toOrderModel);
                return null;
            });
        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }

    // To Order List Note
    public void insertOrderList(ToOrderListModel toOrderListModel) {
        new InsertToOrderListFutureTask(toOrderDao,toOrderListModel).execute();
    }
    public void updateOrderList(ToOrderListModel toOrderListModel) {
        new UpdateToOrderListFutureTask(toOrderDao,toOrderListModel).execute();
    }
    public void deleteOrderList(ToOrderListModel toOrderListModel) {
        new DeleteToOrderListFutureTask(toOrderDao,toOrderListModel).execute();
    }

    public LiveData<List<ToOrderListModel>> getToOrderListData(Long orderId) {
        return toOrderDao.getOrderWithList(orderId);
    }
    public LiveData<Integer> getNumbOfOrderStatus(int orderId){
        return toOrderDao.getNumbOfOrderedStatus(orderId);
    }
    public LiveData<Integer> getNumbOfOrder(int orderId){
        return toOrderDao.getNumbOfOrder(orderId);
    }
    private static class InsertToOrderListFutureTask extends FutureTask<Void> {

        private InsertToOrderListFutureTask(ToOrderDao toOrderDao, ToOrderListModel toOrderListModel) {
            super(() -> {
                toOrderDao.insertOrderList(toOrderListModel);
                return null;
            });
        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }

    private static class UpdateToOrderListFutureTask extends FutureTask<Void> {

        private UpdateToOrderListFutureTask(ToOrderDao toOrderDao, ToOrderListModel toOrderListModel) {
            super(() -> {
                toOrderDao.updateOrderList(toOrderListModel);
                return null;
            });
        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }

    private static class DeleteToOrderListFutureTask extends FutureTask<Void> {

        private DeleteToOrderListFutureTask(ToOrderDao toOrderDao, ToOrderListModel toOrderListModel) {
            super(() -> {
                toOrderDao.deleteOrderList(toOrderListModel);
                return null;
            });
        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }


    //    STOCK NOTE
    public void insertStock(StockModel stockModel) {
        new InsertStockFutureTask(stockDao,stockModel).execute();
    }

    public void updateStock(StockModel stockModel) {
        new UpdateStockFutureTask(stockDao,stockModel).execute();
    }

    public void deleteStock(StockModel stockModel) {
        new DeleteStockFutureTask(stockDao, stockModel).execute();
    }

    public LiveData<List<StockModel>> getStockData() {
        return stockDao.getStockData();
    }

    private static class InsertStockFutureTask extends FutureTask<Void> {

        private InsertStockFutureTask(StockDao stockDao, StockModel stockModel) {
            super(() -> {
                stockDao.insertNewStock(stockModel);
                return null;
            });
        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }
    private static class UpdateStockFutureTask extends FutureTask<Void> {

        private UpdateStockFutureTask(StockDao stockDao, StockModel stockModel) {
            super(() -> {
                stockDao.updateNewStock(stockModel);
                return null;
            });
        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }

    private static class DeleteStockFutureTask extends FutureTask<Void> {

        private DeleteStockFutureTask(StockDao stockDao, StockModel stockModel) {
            super(() -> {
                stockDao.insertNewStock(stockModel);
                return null;
            });
        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }
}
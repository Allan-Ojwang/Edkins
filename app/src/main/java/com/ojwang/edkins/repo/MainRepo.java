package com.ojwang.edkins.repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ojwang.edkins.database.EdkinsDb;
import com.ojwang.edkins.home.homeSubCategory.dao.CreditorDao;
import com.ojwang.edkins.home.homeSubCategory.dao.DebtorDao;
import com.ojwang.edkins.home.homeSubCategory.dao.PaybillDao;
import com.ojwang.edkins.home.homeSubCategory.dao.StockDao;
import com.ojwang.edkins.home.homeSubCategory.dao.StoreDao;
import com.ojwang.edkins.home.homeSubCategory.dao.ToOrderDao;
import com.ojwang.edkins.home.homeSubCategory.dao.WorkerDao;
import com.ojwang.edkins.home.homeSubCategory.model.CreditorModel;
import com.ojwang.edkins.home.homeSubCategory.model.DebtorModel;
import com.ojwang.edkins.home.homeSubCategory.model.PaybillModel;
import com.ojwang.edkins.home.homeSubCategory.model.StockInModel;
import com.ojwang.edkins.home.homeSubCategory.model.StockModel;
import com.ojwang.edkins.home.homeSubCategory.model.StockOutModel;
import com.ojwang.edkins.home.homeSubCategory.model.StockWithQuantityModel;
import com.ojwang.edkins.home.homeSubCategory.model.StoreModel;
import com.ojwang.edkins.home.homeSubCategory.model.ToOrderListModel;
import com.ojwang.edkins.home.homeSubCategory.model.ToOrderModel;
import com.ojwang.edkins.home.homeSubCategory.model.WorkerDebtModel;
import com.ojwang.edkins.home.homeSubCategory.model.WorkerModel;

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
    LiveData<List<StockWithQuantityModel>> stockWithQuantityData;

    private final StoreDao storeDao;
    LiveData<List<StoreModel>> storeData;
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
        stockWithQuantityData = stockDao.getStockWithQuantity();
        storeDao = db.storeDao();
        storeData = storeDao.getStoreData();
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

    public LiveData<List<StockModel>> searchStock(String query){
        return stockDao.searchStock(query);
    }
    public LiveData<List<StockWithQuantityModel>> getStockWithQuantityData(){
        return stockWithQuantityData;
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
                stockDao.deleteNewStock(stockModel);
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

    //    STORE NOTE
    public void insertStore(StoreModel storeModel) {
        new InsertStoreFutureTask(storeDao,storeModel).execute();
    }

    public void updateStore(StoreModel storeModel) {
        new UpdateStoreFutureTask(storeDao,storeModel).execute();
    }

    public void deleteStore(StoreModel storeModel) {
        new DeleteStoreFutureTask(storeDao,storeModel).execute();
    }

    public LiveData<List<StoreModel>> getStoresNotes() {
        return storeData;
    }


    private static class InsertStoreFutureTask extends FutureTask<Void>{

        private InsertStoreFutureTask(StoreDao storeDao, StoreModel storeModel) {
            super(() -> {
                storeDao.insertNewStore(storeModel);
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

    private static class UpdateStoreFutureTask extends FutureTask<Void>{

        private UpdateStoreFutureTask(StoreDao storeDao, StoreModel storeModel) {
            super(() -> {
                storeDao.updateNewStore(storeModel);
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

    private static class DeleteStoreFutureTask extends FutureTask<Void>{

        private DeleteStoreFutureTask(StoreDao storeDao, StoreModel storeModel) {
            super(() -> {
                storeDao.deleteNewStore(storeModel);
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

    //    STOCK IN NOTE
    public void insertStockIn(StockInModel stockInModel) {
        new InsertStockInFutureTask(stockDao,stockInModel).execute();
    }

    public void updateStockIn(StockInModel stockInModel) {
        new UpdateStockInFutureTask(stockDao,stockInModel).execute();
    }

    public void deleteStockIn(StockInModel stockInModel) {
        new DeleteStockInFutureTask(stockDao,stockInModel).execute();
    }

    private static class InsertStockInFutureTask extends FutureTask<Void> {

        private InsertStockInFutureTask(StockDao stockDao, StockInModel stockInModel) {
            super(() -> {
                stockDao.insertStockIn(stockInModel);
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
    private static class UpdateStockInFutureTask extends FutureTask<Void> {

        private UpdateStockInFutureTask(StockDao stockDao, StockInModel stockInModel) {
            super(() -> {
                stockDao.updateStockIn(stockInModel);
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
    private static class DeleteStockInFutureTask extends FutureTask<Void> {

        private DeleteStockInFutureTask(StockDao stockDao, StockInModel stockInModel) {
            super(() -> {
                stockDao.deleteStockIn(stockInModel);
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

    //    STOCK OUT NOTE
    public void insertStockOut(StockOutModel stockOutModel) {
        new InsertStockOutFutureTask(stockDao,stockOutModel).execute();
    }

    public void updateStockOut(StockOutModel stockOutModel) {
        new UpdateStockOutFutureTask(stockDao,stockOutModel).execute();
    }

    public void deleteStockOut(StockOutModel stockOutModel) {
        new DeleteStockOutFutureTask(stockDao,stockOutModel).execute();
    }

    private static class InsertStockOutFutureTask extends FutureTask<Void> {

        private InsertStockOutFutureTask(StockDao stockDao, StockOutModel stockOutModel) {
            super(() -> {
                stockDao.insertStockOut(stockOutModel);
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
    private static class UpdateStockOutFutureTask extends FutureTask<Void> {

        private UpdateStockOutFutureTask(StockDao stockDao, StockOutModel stockOutModel) {
            super(() -> {
                stockDao.updateStockOut(stockOutModel);
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
    private static class DeleteStockOutFutureTask extends FutureTask<Void> {

        private DeleteStockOutFutureTask(StockDao stockDao, StockOutModel stockOutModel) {
            super(() -> {
                stockDao.deleteStockOut(stockOutModel);
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
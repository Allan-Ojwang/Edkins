package com.ojwang.edkins.Repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ojwang.edkins.Database.EdkinsDb;
import com.ojwang.edkins.Home.HomeSubCategory.Dao.CreditorDao;
import com.ojwang.edkins.Home.HomeSubCategory.Dao.DebtorDao;
import com.ojwang.edkins.Home.HomeSubCategory.Dao.PaybillDao;
import com.ojwang.edkins.Home.HomeSubCategory.Dao.WorkerDao;
import com.ojwang.edkins.Home.HomeSubCategory.Model.CreditorModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.DebtorModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.PaybillModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.WorkerModel;

import java.util.List;
import java.util.concurrent.FutureTask;

public class MainRepo {
    private final PaybillDao paybillDao;
    LiveData<List<PaybillModel>> paybillData;
    private final WorkerDao workerDao;
    LiveData<List<WorkerModel>> workerData;

    private final CreditorDao creditorDao;
    LiveData<List<CreditorModel>> creditorData;
    private final LiveData<Float> creditorTotal;


    private final DebtorDao debtorDao;
    LiveData<List<DebtorModel>> debtorData;
    private final LiveData<Float> debtorTotal;

    public MainRepo(Application application){
        EdkinsDb db = EdkinsDb.getInstance(application);
        paybillDao = db.paybillDao();
        paybillData = paybillDao.getPaybillData();
        workerDao = db.workersDao();
        workerData = workerDao.getWorkerData();
        creditorDao = db.creditorDao();
        creditorData = creditorDao.getAllCreditorData();
        creditorTotal = creditorDao.getTotalAmount();
        debtorDao=db.debtorDao();
        debtorData = debtorDao.getAllDebtorData();
        debtorTotal= debtorDao.getTotalAmount();


    }

//    PAYBILL NOTE
    public void insertPaybill(PaybillModel paybillModel){
       new InsertPaybillFutureTask(paybillDao, paybillModel).execute();
    }

    public void updatePaybill(PaybillModel paybillModel){
        new UpdatePaybillAsyncTask(paybillDao).execute(paybillModel);
    }

    public void deletePaybill(PaybillModel paybillModel){
        new DeletePaybillAsyncTask(paybillDao).execute(paybillModel);
    }

    public  LiveData<List<PaybillModel>> getPaybillNotes(){
        return paybillData;
    }

    private static class InsertPaybillFutureTask extends FutureTask<Void> {
        private final PaybillDao paybillDao;
        private final PaybillModel paybillModel;

        private InsertPaybillFutureTask(PaybillDao paybillDao, PaybillModel paybillModel) {
            super(() -> {
                paybillDao.insertPaybill(paybillModel);
                return null;
            });
            this.paybillDao = paybillDao;
            this.paybillModel = paybillModel;
        }

        public void execute() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            super.cancel(mayInterruptIfRunning);
            return mayInterruptIfRunning;
        }
    }

    private static class UpdatePaybillAsyncTask extends AsyncTask<PaybillModel,Void,Void> {
        private final PaybillDao paybillDao;

        private UpdatePaybillAsyncTask(PaybillDao paybillDao){
            this.paybillDao= paybillDao;
        }
        @Override
        protected Void doInBackground(PaybillModel... paybillModels) {
            paybillDao.updatePaybill(paybillModels[0]);
            return null;
        }
    }
    private static class DeletePaybillAsyncTask extends AsyncTask<PaybillModel,Void,Void> {
        private final PaybillDao paybillDao;

        private DeletePaybillAsyncTask(PaybillDao paybillDao){
            this.paybillDao= paybillDao;
        }
        @Override
        protected Void doInBackground(PaybillModel... paybillModels) {
            paybillDao.deletePaybill(paybillModels[0]);
            return null;
        }
    }

    //    WORKERS NOTE
    public void insertWorker(WorkerModel workerModel){
        new InsertWorkerAsyncTask(workerDao).execute(workerModel);
    }

    public void updateWorker(WorkerModel workerModel){
        new UpdateWorkerAsyncTask(workerDao).execute(workerModel);
    }

    public void deleteWorker(WorkerModel workerModel){
        new DeleteWorkerAsyncTask(workerDao).execute(workerModel);
    }

    public  LiveData<List<WorkerModel>> getWorkerNotes(){
        return workerData;
    }

    private static class InsertWorkerAsyncTask extends AsyncTask<WorkerModel,Void,Void> {
        private final WorkerDao workerDao;

        private InsertWorkerAsyncTask(WorkerDao workerDao){
            this.workerDao= workerDao;
        }
        @Override
        protected Void doInBackground(WorkerModel... workerModels) {
            workerDao.insertWorker(workerModels[0]);
            return null;
        }
    }
    private static class UpdateWorkerAsyncTask extends AsyncTask<WorkerModel,Void,Void> {
        private final WorkerDao workerDao;

        private UpdateWorkerAsyncTask(WorkerDao workerDao){
            this.workerDao= workerDao;
        }
        @Override
        protected Void doInBackground(WorkerModel... workerModels) {
            workerDao.updateWorker(workerModels[0]);
            return null;
        }
    }
    private static class DeleteWorkerAsyncTask extends AsyncTask<WorkerModel,Void,Void> {
        private final WorkerDao workerDao;

        private DeleteWorkerAsyncTask(WorkerDao workerDao){
            this.workerDao= workerDao;
        }
        @Override
        protected Void doInBackground(WorkerModel... workerModels) {
            workerDao.deleteWorker(workerModels[0]);
            return null;
        }
    }

    //    CREDITORS NOTE
    public void insertCreditor(CreditorModel creditorModel){
        new InsertCreditorAsyncTask(creditorDao).execute(creditorModel);
    }

    public void updateCreditor(CreditorModel creditorModel){
        new UpdateCreditorAsyncTask(creditorDao).execute(creditorModel);
    }

    public void deleteCreditor(CreditorModel creditorModel){
        new DeleteCreditorAsyncTask(creditorDao).execute(creditorModel);
    }

    public LiveData<List<CreditorModel>> getCreditorNotes(){
        return creditorData;
    }
    public LiveData<Float>getCreditorTotal(){return creditorTotal;}

    private static class InsertCreditorAsyncTask extends AsyncTask<CreditorModel,Void,Void> {
        private final CreditorDao creditorDao;

        private InsertCreditorAsyncTask(CreditorDao creditorDao){
            this.creditorDao= creditorDao;
        }

        @Override
        protected Void doInBackground(CreditorModel... creditorModels) {
            creditorDao.insertCreditor(creditorModels[0]);
            return null;
        }
    }

    private static class UpdateCreditorAsyncTask extends AsyncTask<CreditorModel,Void,Void> {
        private final CreditorDao creditorDao;

        private UpdateCreditorAsyncTask(CreditorDao creditorDao){
            this.creditorDao= creditorDao;
        }

        @Override
        protected Void doInBackground(CreditorModel... creditorModels) {
            creditorDao.updateCreditor(creditorModels[0]);
            return null;
        }
    }

    private static class DeleteCreditorAsyncTask extends AsyncTask<CreditorModel,Void,Void> {
        private final CreditorDao creditorDao;

        private DeleteCreditorAsyncTask(CreditorDao creditorDao){
            this.creditorDao= creditorDao;
        }

        @Override
        protected Void doInBackground(CreditorModel... creditorModels) {
            creditorDao.deleteCreditor(creditorModels[0]);
            return null;
        }
    }

    //    DEBTORS NOTE
    public void insertDebtor(DebtorModel debtorModel){
        new InsertDebtorAsyncTask(debtorDao).execute(debtorModel);
    }
    public void updateDebtor(DebtorModel debtorModel){
        new UpdateDebtorAsyncTask(debtorDao).execute(debtorModel);
    }
    public void deleteDebtor(DebtorModel debtorModel){
        new DeleteDebtorAsyncTask(debtorDao).execute(debtorModel);
    }
    public LiveData<List<DebtorModel>> getDebtorNotes(){
        return debtorData;
    }
    public LiveData<Float>getDebtorTotal(){return debtorTotal;}


    private static class InsertDebtorAsyncTask extends AsyncTask<DebtorModel,Void,Void> {
        private final DebtorDao debtorDao;

        private InsertDebtorAsyncTask(DebtorDao debtorDao){
            this.debtorDao= debtorDao;
        }

        @Override
        protected Void doInBackground(DebtorModel... debtorModels) {
            debtorDao.insertDebtor(debtorModels[0]);
            return null;
        }
    }

    private static class UpdateDebtorAsyncTask extends AsyncTask<DebtorModel,Void,Void> {
        private final DebtorDao debtorDao;

        private UpdateDebtorAsyncTask(DebtorDao debtorDao){
            this.debtorDao= debtorDao;
        }

        @Override
        protected Void doInBackground(DebtorModel... debtorModels) {
            debtorDao.updateDebtor(debtorModels[0]);
            return null;
        }
    }

    private static class DeleteDebtorAsyncTask extends AsyncTask<DebtorModel,Void,Void> {
        private final DebtorDao debtorDao;

        private DeleteDebtorAsyncTask(DebtorDao debtorDao){
            this.debtorDao= debtorDao;
        }

        @Override
        protected Void doInBackground(DebtorModel... debtorModels) {
            debtorDao.deleteDebtor(debtorModels[0]);
            return null;
        }
    }

}

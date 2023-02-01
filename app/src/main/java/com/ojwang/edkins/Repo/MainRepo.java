package com.ojwang.edkins.Repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ojwang.edkins.Database.EdkinsDb;
import com.ojwang.edkins.Home.HomeSubCategory.Dao.PaybillDao;
import com.ojwang.edkins.Home.HomeSubCategory.Dao.WorkerDao;
import com.ojwang.edkins.Home.HomeSubCategory.Model.PaybillModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.WorkerModel;

import java.util.List;

public class MainRepo {
    private PaybillDao paybillDao;
    LiveData<List<PaybillModel>> paybillData;
    private WorkerDao workerDao;
    LiveData<List<WorkerModel>> workerData;

    public MainRepo(Application application){
        EdkinsDb db = EdkinsDb.getInstance(application);
        paybillDao = db.paybillDao();
        paybillData = paybillDao.getPaybillData();
        workerDao = db.workersDao();
        workerData = workerDao.getWorkerData();

    }

//    PAYBILL NOTE
    public void insertPaybill(PaybillModel paybillModel){
        new InsertPaybillAsyncTask(paybillDao).execute(paybillModel);
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

    private static class InsertPaybillAsyncTask extends AsyncTask<PaybillModel,Void,Void> {
        private PaybillDao paybillDao;

        private InsertPaybillAsyncTask(PaybillDao paybillDao){
            this.paybillDao= paybillDao;
        }
        @Override
        protected Void doInBackground(PaybillModel... paybillModels) {
            paybillDao.insertPaybill(paybillModels[0]);
            return null;
        }
    }
    private static class UpdatePaybillAsyncTask extends AsyncTask<PaybillModel,Void,Void> {
        private PaybillDao paybillDao;

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
        private PaybillDao paybillDao;

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
        private WorkerDao workerDao;

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
        private WorkerDao workerDao;

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
        private WorkerDao workerDao;

        private DeleteWorkerAsyncTask(WorkerDao workerDao){
            this.workerDao= workerDao;
        }
        @Override
        protected Void doInBackground(WorkerModel... workerModels) {
            workerDao.deleteWorker(workerModels[0]);
            return null;
        }
    }
}

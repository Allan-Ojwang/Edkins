package com.ojwang.edkins.Home.HomeSubCategory.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ojwang.edkins.Home.HomeSubCategory.Model.WorkerDebtModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.WorkerModel;
import java.util.List;

@Dao
public interface WorkerDao {
    @Insert
    void insertWorker(WorkerModel workerModel);

    @Insert
    void insertWorkerDebt(WorkerDebtModel workerDebtModel);

    @Update
    void updateWorker(WorkerModel workerModel);

    @Update
    void updateWorkerDebt(WorkerDebtModel workerDebtModel);

    @Delete
    void deleteWorker(WorkerModel workerModel);
    @Delete
    void deleteWorkerDebt(WorkerDebtModel workerDebtModel);

    @Query("SELECT * FROM worker_table")
    LiveData<List<WorkerModel>> getWorkerData();

    @Query("SELECT * FROM worker_debt_table WHERE workerId = :workerId")
    LiveData<List<WorkerDebtModel>> getWorkerWithDebt (int workerId);

    @Query("SELECT IFNULL(SUM(amount), 0) as totalLoan FROM worker_debt_table WHERE dStatus = 'Loan' ")
    LiveData<Float> getTotalLoan();

    @Query("SELECT IFNULL(SUM(amount), 0) as totalSaving FROM worker_debt_table WHERE dStatus = 'Saving' ")
    LiveData<Float> getTotalSaving();

}

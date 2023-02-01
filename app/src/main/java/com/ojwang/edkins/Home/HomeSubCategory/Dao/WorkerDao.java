package com.ojwang.edkins.Home.HomeSubCategory.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ojwang.edkins.Home.HomeSubCategory.Model.WorkerModel;

import java.util.List;

@Dao
public interface WorkerDao {
    @Insert
    void insertWorker(WorkerModel workerModel);

    @Update
    void updateWorker(WorkerModel workerModel);

    @Delete
    void deleteWorker(WorkerModel workerModel);

    @Query("SELECT * FROM worker_table")
    LiveData<List<WorkerModel>> getWorkerData();
}

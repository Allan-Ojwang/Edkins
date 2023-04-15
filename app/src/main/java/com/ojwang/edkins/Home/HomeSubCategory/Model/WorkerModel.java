package com.ojwang.edkins.Home.HomeSubCategory.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "worker_table")
public class WorkerModel {
    @PrimaryKey(autoGenerate = true)
    private int workerId;
    private final String worker_name;
    private final int number;
    private final int id_number;

    public WorkerModel(String worker_name, int number, int id_number) {
        this.worker_name = worker_name;
        this.number = number;
        this.id_number = id_number;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getWorker_name() {
        return worker_name;
    }

    public int getNumber() {
        return number;
    }

    public int getId_number() {
        return id_number;
    }
}

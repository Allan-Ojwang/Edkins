package com.ojwang.edkins.Home.HomeSubCategory.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "worker_table")
public class WorkerModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String worker_name;
    private int amount;

    public WorkerModel(String worker_name, int amount) {
        this.worker_name = worker_name;
        this.amount = amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getWorker_name() {
        return worker_name;
    }

    public int getAmount() {
        return amount;
    }
}

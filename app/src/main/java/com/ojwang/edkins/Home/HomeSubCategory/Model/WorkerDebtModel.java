package com.ojwang.edkins.Home.HomeSubCategory.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "worker_debt_table", foreignKeys = @ForeignKey(entity = WorkerModel.class, parentColumns = "workerId",childColumns = "workerid",onDelete = ForeignKey.CASCADE   ))
public class WorkerDebtModel {
    @PrimaryKey(autoGenerate = true)
    private int debtId;
    private final String date;
    private final int amount;
    @ColumnInfo(name = "workerid", index = true)
    private final int workerId;

    public WorkerDebtModel(String date, int amount, int workerId) {
        this.date = date;
        this.amount = amount;
        this.workerId = workerId;
    }

    public void setDebtId(int debtId) {
        this.debtId = debtId;
    }

    public int getDebtId() {
        return debtId;
    }

    public String getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }

    public int getWorkerId() {
        return workerId;
    }
}

package com.ojwang.edkins.Home.HomeSubCategory.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "creditor_table")
public class CreditorModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    public String name, reason;
    public int amount;
    public boolean pStatus = false;

    public CreditorModel(String name, String reason, int amount) {
        this.name = name;
        this.reason = reason;
        this.amount = amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getReason() {
        return reason;
    }
    public int getAmount() {
        return amount;
    }
    public boolean ispStatus() {
        return pStatus;
    }
    public void setpStatus(boolean pStatus) {
        this.pStatus = pStatus;
    }

}

package com.ojwang.edkins.Home.HomeSubCategory.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "paybill_table")
public class PaybillModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name,tillPay,accNo,status;


    public PaybillModel(String name, String tillPay, String status) {
        this.name = name;
        this.tillPay = tillPay;
        this.status = status;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getTillPay() {
        return tillPay;
    }

    public void setTillPay(String tillPay) {
        this.tillPay = tillPay;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

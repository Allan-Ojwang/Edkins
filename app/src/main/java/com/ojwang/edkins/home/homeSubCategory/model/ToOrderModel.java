package com.ojwang.edkins.home.homeSubCategory.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "to_order_table")
public class ToOrderModel {

    @PrimaryKey(autoGenerate = true)
    private int orderId;

    private final int year;
    private final String month;
    private final int date;

    public String status = "Processing";

    public ToOrderModel(int year, String month, int date) {
        this.year = year;
        this.month = month;
        this.date = date;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public int getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

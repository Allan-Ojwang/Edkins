package com.ojwang.edkins.home.homeSubCategory.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "to_order_list_table",
        foreignKeys = @ForeignKey(entity = ToOrderModel.class,
                parentColumns = "orderId",
                childColumns = "orderid",
                onDelete = ForeignKey.CASCADE))
public class ToOrderListModel {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String item;
    public boolean oStatus = false;

    @ColumnInfo(name = "orderid", index = true)
    private final int orderId;

    public ToOrderListModel(String item, int orderId, boolean oStatus) {
        this.item = item;
        this.orderId = orderId;
        this.oStatus = oStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setoStatus(boolean oStatus) {
        this.oStatus = oStatus;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public String getItem() {
        return item;
    }

    public boolean isoStatus() {
        return oStatus;
    }

    public int getOrderId() {
        return orderId;
    }

}

package com.ojwang.edkins.Home.HomeSubCategory.Model;

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

    private final String item;
    private final int quantity;
    public boolean oStatus = false;

    @ColumnInfo(name = "orderid", index = true)
    private final int orderId;

    public ToOrderListModel(String item, int quantity, int orderId) {
        this.item = item;
        this.quantity = quantity;
        this.orderId = orderId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setoStatus(boolean oStatus) {
        this.oStatus = oStatus;
    }

    public int getId() {
        return id;
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isoStatus() {
        return oStatus;
    }

    public int getOrderId() {
        return orderId;
    }
}

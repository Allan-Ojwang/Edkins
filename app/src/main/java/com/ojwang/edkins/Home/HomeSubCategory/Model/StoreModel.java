package com.ojwang.edkins.Home.HomeSubCategory.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "store_table")
public class StoreModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String productName;

    public StoreModel(String productName) {
        this.productName = productName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}

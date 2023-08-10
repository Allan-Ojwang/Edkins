package com.ojwang.edkins.home.homeSubCategory.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "store_table")
public class StoreModel {
    @PrimaryKey(autoGenerate = true)
    private int storeId;
    private String storeName;

    public StoreModel(String storeName) {
        this.storeName = storeName;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

}

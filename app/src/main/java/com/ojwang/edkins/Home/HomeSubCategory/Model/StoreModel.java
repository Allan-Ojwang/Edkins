package com.ojwang.edkins.Home.HomeSubCategory.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "store_table")
public class StoreModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String storeName;

    public StoreModel(String storeName) {
        this.storeName = storeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}

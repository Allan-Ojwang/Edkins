package com.ojwang.edkins.home.homeSubCategory.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stock_table")
public class StockModel {
    @PrimaryKey(autoGenerate = true)
    private int stockId;
    private String productName;
    private int sellingPrice;

    public StockModel(String productName, int sellingPrice) {
        this.productName = productName;
        this.sellingPrice = sellingPrice;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}

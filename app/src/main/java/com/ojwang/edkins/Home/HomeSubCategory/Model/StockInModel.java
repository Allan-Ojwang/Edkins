package com.ojwang.edkins.Home.HomeSubCategory.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "stock_in_table", foreignKeys = {
        @ForeignKey(entity = StoreModel.class, parentColumns = "storeId", childColumns = "store_id", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = StockModel.class, parentColumns = "stockId", childColumns = "good_id", onDelete = ForeignKey.CASCADE)
})
public class StockInModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int year;
    private String month;
    private int day;

    @ColumnInfo(name = "good_id", index = true)
    private final int stockId;
    private String productName;

    @ColumnInfo(name = "store_id", index = true)
    private final int storeId;
    private String storeName;

    private int buyingPrice;
    private int quantity;

    public StockInModel(int year, String month, int day, int stockId, String productName, int storeId, String storeName, int buyingPrice, int quantity) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.stockId = stockId;
        this.productName = productName;
        this.storeId = storeId;
        this.storeName = storeName;
        this.buyingPrice = buyingPrice;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStockId() {
        return stockId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(int buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

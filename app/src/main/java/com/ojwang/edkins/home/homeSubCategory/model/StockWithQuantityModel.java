package com.ojwang.edkins.home.homeSubCategory.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity( tableName = "stock_with_quantity_table")
public class StockWithQuantityModel {

//    @Embedded
//    public StockModel stockModel;
//
////    @PrimaryKey
////    private int stock_id = stockModel.getStockId();
//    private String product_name = stockModel.getProductName();
//    private int selling_price = stockModel.getSellingPrice();


    @PrimaryKey
    public int stockId;
    public String productName;
    public int sellingPrice;

    @ColumnInfo(name = "totalQuantity")
    public int totalQuantity;

    public StockWithQuantityModel(int stockId, String productName, int sellingPrice, int totalQuantity) {
        this.stockId = stockId;
        this.productName = productName;
        this.sellingPrice = sellingPrice;
        this.totalQuantity = totalQuantity;
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

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    //    public int getTotalQuantity() {
//        return totalQuantity;
//    }
//
//    public void setTotalQuantity(int totalQuantity) {
//        this.totalQuantity = totalQuantity;
//    }

//    public int getStock_id() {
//        return stock_id;
//    }
//
//    public void setStock_id(int stock_id) {
//        this.stock_id = stock_id;
//    }
//
//    public String getProduct_name() {
//        return product_name;
//    }
//
//    public void setProduct_name(String product_name) {
//        this.product_name = product_name;
//    }
//
//    public int getSelling_price() {
//        return selling_price;
//    }
//
//    public void setSelling_price(int selling_price) {
//        this.selling_price = selling_price;
//    }
}

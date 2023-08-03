package com.ojwang.edkins.Home.HomeSubCategory.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ojwang.edkins.Home.HomeSubCategory.Model.StockInModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.StockModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.StockOutModel;

import java.util.List;

@Dao
public interface StockDao {
    @Insert
    void insertNewStock(StockModel stockModel);
    @Update
    void updateNewStock(StockModel stockModel);
    @Delete
    void deleteNewStock(StockModel stockModel);

    @Query("SELECT * FROM stock_table")
    LiveData<List<StockModel>> getStockData();

    @Query("SELECT * FROM stock_table WHERE productName LIKE :query")
    LiveData<List<StockModel>> searchStock(String query);

    @Insert
    void insertStockIn(StockInModel stockInModel);
    @Update
    void updateStockIn(StockInModel stockInModel);
    @Delete
    void deleteStockIn(StockInModel stockInModel);

    @Insert
    void insertStockOut(StockOutModel stockOutModel);
    @Update
    void updateStockOut(StockOutModel stockOutModel);
    @Delete
    void deleteStockOut(StockOutModel stockOutModel);
}

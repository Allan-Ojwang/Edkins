package com.ojwang.edkins.home.homeSubCategory.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ojwang.edkins.home.homeSubCategory.model.StockInModel;
import com.ojwang.edkins.home.homeSubCategory.model.StockModel;
import com.ojwang.edkins.home.homeSubCategory.model.StockOutModel;
import com.ojwang.edkins.home.homeSubCategory.model.StockWithQuantityModel;

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

    @Query("SELECT * FROM stock_in_table WHERE store_id = :storeId AND good_id = :stockId")
    LiveData<List<StockInModel>> getStockInData(int storeId, int stockId);

    @Query("SELECT stock_table.stockId,stock_table.productName,stock_table.sellingPrice,"+
            "COALESCE(SUM(stock_in_table.quantity),0) - COALESCE(SUM(stock_out_table.quantity),0) AS totalQuantity" +
            " FROM stock_table "+
            "LEFT JOIN stock_in_table ON stock_table.stockId = stock_in_table.good_id" + " " +
            "LEFT JOIN STOCK_OUT_TABLE ON stock_table.stockId = stock_out_table.good_id" + " " +
            "GROUP BY stock_table.stockId,stock_table.productName,stock_table.sellingPrice ")
    LiveData<List<StockWithQuantityModel>> getStockWithQuantity();

    @Query("SELECT SUM(quantity) FROM stock_in_table WHERE good_id = :stockId AND store_id = :storeId")
    int getTotalStockInQuantity(long stockId, long storeId);
    @Query("SELECT SUM(quantity) FROM stock_out_table WHERE good_id = :stockId AND store_id = :storeId")
    int getTotalStockOutQuantity(long stockId, long storeId);


    @Query("SELECT * FROM stock_out_table WHERE store_id = :storeId AND good_id = :stockId")
    LiveData<List<StockOutModel>> getStockOutData(int storeId, int stockId);

}

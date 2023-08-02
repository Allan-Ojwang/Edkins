package com.ojwang.edkins.Home.HomeSubCategory.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ojwang.edkins.Home.HomeSubCategory.Model.StoreModel;

import java.util.List;

@Dao
public interface StoreDao {
    @Insert
    void insertNewStore(StoreModel storeModel);
    @Update
    void updateNewStore(StoreModel storeModel);
    @Delete
    void deleteNewStore(StoreModel storeModel);

    @Query("SELECT * FROM store_table")
    LiveData<List<StoreModel>> getStoreData();
}

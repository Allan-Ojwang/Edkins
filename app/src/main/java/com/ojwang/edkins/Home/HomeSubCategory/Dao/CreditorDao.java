package com.ojwang.edkins.Home.HomeSubCategory.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ojwang.edkins.Home.HomeSubCategory.Model.CreditorModel;

import java.util.List;

@Dao
public interface CreditorDao {
    @Insert
    void insertCreditor(CreditorModel creditorModel);

    @Delete
    void deleteCreditor(CreditorModel creditorModel);

    @Update
    void updateCreditor(CreditorModel creditorModel);

    @Query("SELECT * FROM creditor_table WHERE pStatus = 0")
    LiveData<List<CreditorModel>> getAllCreditorData();
    @Query("SELECT * FROM creditor_table WHERE pStatus = 1")
    LiveData<List<CreditorModel>> getPaidCreditorData();

    @Query("SELECT IFNULL(SUM(amount), 0) as totalAmount FROM creditor_table WHERE pStatus = 0")
    LiveData<Float> getTotalAmount();


}

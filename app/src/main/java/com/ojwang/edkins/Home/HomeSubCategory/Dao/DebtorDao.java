package com.ojwang.edkins.Home.HomeSubCategory.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ojwang.edkins.Home.HomeSubCategory.Model.CreditorModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.DebtorModel;

import java.util.List;

@Dao
public interface DebtorDao {

    @Insert
    void insertDebtor(DebtorModel debtorModel);

    @Delete
    void deleteDebtor(DebtorModel debtorModel);

    @Update
    void updateDebtor(DebtorModel debtorModel);

    @Query("SELECT * FROM debtor_table ORDER BY pStatus ASC")
    LiveData<List<DebtorModel>> getAllDebtorData();


    @Query("SELECT IFNULL(SUM(amount), 0) as totalAmount FROM debtor_table WHERE pStatus = 0")
    LiveData<Float> getTotalAmount();

}

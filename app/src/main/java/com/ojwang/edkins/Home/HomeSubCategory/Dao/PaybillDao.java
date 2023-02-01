package com.ojwang.edkins.Home.HomeSubCategory.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ojwang.edkins.Home.HomeSubCategory.Model.PaybillModel;

import java.util.List;

@Dao
public interface PaybillDao {
        @Insert
        void insertPaybill(PaybillModel paybillModel);

        @Update
        void updatePaybill(PaybillModel paybillModel);

        @Delete
        void deletePaybill(PaybillModel paybillModel);

        @Query("SELECT * FROM paybill_table")
        LiveData<List<PaybillModel>>getPaybillData();
}

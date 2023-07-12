package com.ojwang.edkins.Home.HomeSubCategory.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ojwang.edkins.Home.HomeSubCategory.Model.ToOrderListModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.ToOrderModel;
import java.util.List;

@Dao
public interface ToOrderDao {
    @Insert
    void insertOrder(ToOrderModel toOrderModel);
    @Insert
    void insertOrderList(ToOrderListModel toOrderListModel);

    @Update
    void updateOrder(ToOrderModel toOrderModel);
    @Update
    void updateOrderList(ToOrderListModel toOrderListModel);

    @Delete
    void deleteOrder(ToOrderModel toOrderModel);
    @Delete
    void deleteOrderList(ToOrderListModel toOrderListModel);

    @Query("SELECT * FROM to_order_table")
    LiveData<List<ToOrderModel>> getToOrderData();

    @Query("SELECT * FROM TO_ORDER_LIST_TABLE WHERE orderid = :orderId ORDER BY oStatus ASC")
    LiveData<List<ToOrderListModel>> getOrderWithList (int orderId);

    @Query("SELECT COUNT(id) FROM TO_ORDER_LIST_TABLE WHERE oStatus = 1 AND orderid = :orderId")
    LiveData<Integer> getNumbOfOrderedStatus(int orderId);

    @Query("SELECT COUNT(id) FROM TO_ORDER_LIST_TABLE WHERE orderid = :orderId")
    LiveData<Integer> getNumbOfOrder (int orderId);

}

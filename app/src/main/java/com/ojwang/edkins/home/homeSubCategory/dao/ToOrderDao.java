
package com.ojwang.edkins.home.homeSubCategory.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ojwang.edkins.home.homeSubCategory.model.ToOrderListModel;
import com.ojwang.edkins.home.homeSubCategory.model.ToOrderModel;
import java.util.List;

@Dao
public interface ToOrderDao {
    @Insert
    long insertOrder(ToOrderModel toOrderModel);

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

    @Query("SELECT * FROM to_order_table ORDER BY status DESC")
    LiveData<List<ToOrderModel>> getToOrderData();

    @Query("SELECT * FROM TO_ORDER_LIST_TABLE WHERE orderid = :orderId ORDER BY oStatus ASC")
    LiveData<List<ToOrderListModel>> getOrderWithList (Long orderId);

    @Query("SELECT COUNT(item) FROM TO_ORDER_LIST_TABLE WHERE orderid = :orderId AND oStatus = 1")
    LiveData<Integer> getNumbOfOrderedStatus(int orderId);

    @Query("SELECT COUNT(item) FROM TO_ORDER_LIST_TABLE WHERE orderid = :orderId")
    LiveData<Integer> getNumbOfOrder (int orderId);

}

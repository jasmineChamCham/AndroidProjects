package com.example.burgermenu;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM 'Order'")
    List<Order> getAllOrders();

    @Insert
    void insert(Order ...orders);

    @Delete
    void delete(Order order);

    @Update
    void update(Order order);
}

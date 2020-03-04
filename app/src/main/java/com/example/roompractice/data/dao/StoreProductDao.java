package com.example.roompractice.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface StoreProductDao {

    //adds a new item to the cross reference table
    @Insert
    void addStoreProductItem(Long storeId, Long productId);
}

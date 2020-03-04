package com.example.roompractice.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roompractice.model.Store;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface StoreDao {

    //get the store items
    @Query("SELECT * FROM store")
    Flowable<List<Store>> getStoreItems();

    //insert a new store item
    @Insert
    Single<Long> insertStoreItem(Store storeItem);
}

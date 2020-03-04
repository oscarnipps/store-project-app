package com.example.roompractice.data.dao;


import androidx.room.Dao;
import androidx.room.Insert;

import com.example.roompractice.model.Product;

import io.reactivex.Single;

@Dao
public interface ProductDao {

    @Insert()
    Single<Long> addProductToStore(Product productItem);
}

package com.example.roompractice.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roompractice.model.Product;
import com.example.roompractice.model.StoreProductJoin;

import java.util.List;

@Dao
public interface StoreProductJoinDao {

    //add a product to a created store via foreign key constraint
    @Insert()
    void addProductToStore(StoreProductJoin storeProductItem);


    @Query("SELECT id, product_name, product_amount ,created_at FROM product_table" +
            " INNER JOIN store_product_table ON product_table.id = store_product_table.productId " +
            "WHERE store_product_table.storeId = :storeId")
    LiveData<List<Product>> getProductsInStore(int storeId);

}

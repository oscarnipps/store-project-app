package com.example.roompractice.model;

/*
* Created by Oscar Ekesiobi 2/26/2020
*
* This class serves as an associative table that holds the store and product items
*
*/

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "store_product_table", primaryKeys = {"storeId","productId"})
public class StoreProductRef {

    @ColumnInfo(name = "store_id")
    private Long storeId;
    @ColumnInfo(name = "product_id")
    private Long ProductId;

    public StoreProductRef(Long storeId, Long productId) {
        this.storeId = storeId;
        ProductId = productId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getProductId() {
        return ProductId;
    }

    public void setProductId(Long productId) {
        ProductId = productId;
    }
}

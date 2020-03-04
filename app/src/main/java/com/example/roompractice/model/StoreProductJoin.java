package com.example.roompractice.model;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        indices = @Index("storeId"),
        tableName = "store_product_table",
        primaryKeys = {"productId" , "storeId"},
        foreignKeys = {
                @ForeignKey(
                        entity = Product.class,
                        parentColumns = "id",
                        childColumns = "productId",
                        onDelete = CASCADE,
                        onUpdate = CASCADE
                ),
                @ForeignKey(
                        entity = Store.class,
                        parentColumns = "id",
                        childColumns = "storeId",
                        onDelete = CASCADE,
                        onUpdate = CASCADE
                )
        }
)
public class StoreProductJoin {

    public int productId;
    public int storeId;

    public StoreProductJoin(int productId, int storeId) {
        this.productId = productId;
        this.storeId = storeId;
    }
}

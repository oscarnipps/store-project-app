package com.example.roompractice.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "store"
    )
public class Store {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "storeId")
    public Long id;

    @ColumnInfo(name = "store_name")
    public String storeName;

    @ColumnInfo(name = "created_at")
    public String createdAt;

    @ColumnInfo(name = "store_owner")
    private Long storeOwnerId;


    public Store(Long id, String storeName, String createdAt , Long storeOwnerId) {
        this.id = id;
        this.storeName = storeName;
        this.createdAt = createdAt;
        this.storeOwnerId = storeOwnerId;
    }

    @Ignore
    public Store(String storeName, String createdAt ,Long storeOwnerId) {
        this.storeName = storeName;
        this.createdAt = createdAt;
        this.storeOwnerId = storeOwnerId;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getStoreOwnerId() {
        return storeOwnerId;
    }

    public void setStoreOwnerId(Long storeOwnerId) {
        this.storeOwnerId = storeOwnerId;
    }
}

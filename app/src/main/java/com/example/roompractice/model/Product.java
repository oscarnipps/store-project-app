package com.example.roompractice.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "product"
)
public class Product {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "productId")
    private Long id;

    @ColumnInfo(name = "product_name")
    private String productName;

    @ColumnInfo(name = "product_count")
    private int productCount;

    @ColumnInfo(name = "created_at")
    private String createdAt;


    public Product(Long id, String productName, int productCount, String createdAt) {
        this.id = id;
        this.productName = productName;
        this.productCount = productCount;
        this.createdAt = createdAt;
    }

    public Product(String productName, int productCount, String createdAt) {
        this.productName = productName;
        this.productCount = productCount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

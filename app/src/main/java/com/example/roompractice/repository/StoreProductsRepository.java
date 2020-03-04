package com.example.roompractice.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.roompractice.data.StoreListDatabase;
import com.example.roompractice.data.dao.ProductDao;
import com.example.roompractice.data.dao.StoreProductDao;
import com.example.roompractice.model.Product;

import io.reactivex.Single;

public class StoreProductsRepository{
    public static final String TAG = StoreProductsRepository.class.getSimpleName();
    private ProductDao productDao;
    private StoreProductDao storeProductDao;
    private static StoreProductsRepository mStoreRepository;
    private static StoreListDatabase mDatabase;
    private long productId;
    private MutableLiveData<Long> productInsertId = new MutableLiveData<>();



    public StoreProductsRepository(Context context) {
        mDatabase = StoreListDatabase.getInstance(context);
        productDao = mDatabase.getProductDao();
        storeProductDao = mDatabase.getStoreProductDao();
    }


    //add new product to the store
    public Single<Long> addProductToStore(Product product){
        return productDao.addProductToStore(product);
    }

    public void addItemToStoreProductTable(Long storeId, Long productId) {
        storeProductDao.addStoreProductItem(storeId ,productId);
    }


/*
    //add to store_product cross reference table
    private void addStoreProductItemToStore(Long storeId, Long productId) {
        // new store_product cross ref item
        StoreProductJoin storeProductItem = new StoreProductJoin((int) productId, storeId);
        storeProductJoinDao.addProductToStore(storeProductItem);
    }*/


}

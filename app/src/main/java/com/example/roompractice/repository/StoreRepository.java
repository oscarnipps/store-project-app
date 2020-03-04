package com.example.roompractice.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.roompractice.data.StoreListDatabase;
import com.example.roompractice.data.dao.StoreDao;
import com.example.roompractice.model.Store;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class StoreRepository {
    private static final String TAG = StoreRepository.class.getSimpleName();
    private MutableLiveData<List<Store>> mStoreItems = new MutableLiveData<>();
    private final StoreDao storeDao;

    private long insertId;

    public StoreRepository(Context context) {
        StoreListDatabase database = StoreListDatabase.getInstance(context);
        storeDao = database.getStoreDao();
        Log.d(TAG, "accessing store-dao from repository................ " + storeDao);
    }

    public Flowable<List<Store>> getStoreList() {
        Log.d(TAG, "fetching store lists from repository...");
        return storeDao.getStoreItems();
    }

    //insert new store item
    public Single<Long> insertStoreItem(Store store) {
        return storeDao.insertStoreItem(store);
    }
}

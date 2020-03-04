package com.example.roompractice.repository;

import android.app.Application;
import android.util.Log;

import com.example.roompractice.data.StoreListDatabase;
import com.example.roompractice.data.dao.OwnerDao;
import com.example.roompractice.model.Owner;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class StoreOwnerRepository {

    private StoreListDatabase database;
    private OwnerDao ownerDao;
    public static final String TAG = StoreOwnerRepository.class.getSimpleName();

    public StoreOwnerRepository(Application application) {
        Log.d(TAG, "database instance created from repository");
        database = StoreListDatabase.getInstance(application);
        ownerDao = database.getOwnerDao();
    }


    public Single<Long> createNewOwner(Owner owner) {
        return ownerDao.addNewStoreUser(owner);
    }

    public Flowable<List<Owner>> getAllOwners() {
        return ownerDao.getAllOwners();
    }


/*    //get the list of store owners
    public Flowable<List<Owner>> getStoreOwnersFromDatabse() {
       return database.getStoreOwnerDao().getOwners();
    }

    //insert owner to Database
    public Single<Long> insertOwner(Owner owner) {
        Single<Long> id = database.getStoreOwnerDao().insertOwner(owner);
        return id;
    }*/
}

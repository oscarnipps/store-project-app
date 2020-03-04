package com.example.roompractice.data;


import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roompractice.data.dao.OwnerDao;
import com.example.roompractice.data.dao.ProductDao;
import com.example.roompractice.data.dao.StoreDao;
import com.example.roompractice.data.dao.StoreProductDao;
import com.example.roompractice.data.dao.StoreProductJoinDao;
import com.example.roompractice.model.Product;
import com.example.roompractice.model.Store;
import com.example.roompractice.model.StoreProductJoin;
import com.example.roompractice.model.Owner;

@Database(
        entities = {
                Owner.class,
                Product.class,
                StoreProductJoin.class,
                Store.class
        },
        exportSchema = false,
        version = 1
    )
public abstract class StoreListDatabase extends RoomDatabase {

    private static StoreListDatabase databaseInstance;
    private static final String DATABASE_NAME = "store_database.db";
    public static final String TAG = StoreListDatabase.class.getSimpleName();
    public abstract StoreDao getStoreDao();
    public abstract ProductDao getProductDao();
    public abstract OwnerDao getOwnerDao();
    public abstract StoreProductDao getStoreProductDao();
    public abstract StoreProductJoinDao getStoreProductJoin();

    public static StoreListDatabase getInstance(Context context) {

        if (databaseInstance == null) {
            //build database instance
            databaseInstance = createDatabaseInstance(context);
            return databaseInstance;
        } else {
            return databaseInstance;
        }
    }

    private static StoreListDatabase createDatabaseInstance(final Context context) {
        //build database
        Log.d(TAG,"building database instance ......");
        databaseInstance = Room.databaseBuilder(
                context,
                StoreListDatabase.class,
                DATABASE_NAME
        ).build();

        return databaseInstance;
    }
}

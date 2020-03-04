package com.example.roompractice.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roompractice.model.Owner;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface OwnerDao {

    //add a new store owner
    @Insert
    Single<Long> addNewStoreUser(Owner owner);

    @Query("SELECT * FROM owner")
    Flowable<List<Owner>> getAllOwners();
}

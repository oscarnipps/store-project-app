package com.example.roompractice.ui.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roompractice.model.Store;
import com.example.roompractice.repository.StoreRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class StoreListViewModel extends AndroidViewModel {
    private static final String TAG = StoreListViewModel.class.getSimpleName();
    private StoreRepository storeRepository;
    private LiveData<List<Store>> storeList;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public StoreListViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG , "initializing view model");
        // initialize repository
        storeRepository = new StoreRepository(application);
    }

    public Flowable<List<Store>> getStoreList() {
        return  storeRepository.getStoreList();
    }

    public void createNewStore(Store store) {
       compositeDisposable.add( storeRepository.insertStoreItem(store)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Long>() {
                    @Override
                    public void onSuccess(Long aLong) {
                        Log.d(TAG , "inserted store id --> ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG , "error inserting store --> " + e.getLocalizedMessage());
                    }
                })
       );
    }


}

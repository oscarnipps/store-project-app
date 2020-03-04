package com.example.roompractice.ui.viewmodels;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.roompractice.model.Owner;
import com.example.roompractice.repository.StoreOwnerRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class StoreOwnerViewModel extends AndroidViewModel {
    /*private Flowable<List<StoreOwner>> mStoreOwners;*/
    private StoreOwnerRepository storeOwnerRepository;
    public static final String TAG = StoreOwnerViewModel.class.getSimpleName();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;

    public StoreOwnerViewModel(@NonNull Application application) {
        super(application);
        //initialize repository
        storeOwnerRepository = new StoreOwnerRepository(application);

    }

    public void createNewStoreOwner(Owner owner) {
        compositeDisposable.add( storeOwnerRepository.createNewOwner(owner)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Long>() {

                    @Override
                    public void onSuccess(Long aLong) {
                        Log.d(TAG , "added successfully");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG ,"error ---- > " + e.getLocalizedMessage());
                    }
                })
        );
    }

    public Flowable<List<Owner>> getAllOwners() {
        return storeOwnerRepository.getAllOwners();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null) {
            //clear disposable
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }
}

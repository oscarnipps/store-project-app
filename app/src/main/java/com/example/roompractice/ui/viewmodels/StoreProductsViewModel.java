package com.example.roompractice.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roompractice.model.Product;
import com.example.roompractice.repository.StoreProductsRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class StoreProductsViewModel extends AndroidViewModel {
    public StoreProductsRepository storeProductsRepository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public StoreProductsViewModel(@NonNull Application application) {
        super(application);
        //initialize repository
        storeProductsRepository = new StoreProductsRepository(application);
    }

/*    public long addProductToStore(int storeId, int productCount, String productName) throws ExecutionException, InterruptedException {
        Date date =new Date();
        Product productItem = new Product(productCount,productName,date);
        return storeProductsRepository.addProductToStore(productItem , storeId);

    }*/

    public LiveData<List<Product>> getProductsInStore(int storeId) {
       return storeProductsRepository.getProductsInStore(storeId);
    }

    public void addProductToStore(Long storeId, Product item) {
        compositeDisposable.add( storeProductsRepository.addProductToStore(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Long>() {
                    @Override
                    public void onSuccess(Long productInsertId) {
                        //get the id of the inserted product item and add to the cross reference table
                        addItemToStoreProductAssociativeTable(storeId ,productInsertId);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                })
        );
    }

    private void addItemToStoreProductAssociativeTable(Long storeId, Long productInsertId) {
        storeProductsRepository.addItemToStoreProductTable(storeId ,productInsertId);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null) {
            //clea disposable
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }
}

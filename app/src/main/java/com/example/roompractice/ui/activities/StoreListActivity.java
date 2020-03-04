package com.example.roompractice.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roompractice.R;
import com.example.roompractice.databinding.ActivityStoreListBinding;
import com.example.roompractice.model.Store;
import com.example.roompractice.ui.adapter.StoreListAdapter;
import com.example.roompractice.ui.fragments.AddStoreDialogFragment;
import com.example.roompractice.ui.viewmodels.StoreListViewModel;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

import static com.example.roompractice.data.Constant.STORE_ID;
import static com.example.roompractice.data.Constant.STORE_NAME;
import static com.example.roompractice.data.Constant.STORE_OWNER_ID;
import static com.example.roompractice.data.Constant.STORE_OWNER_NAME;

public class StoreListActivity extends AppCompatActivity implements
        AddStoreDialogFragment.AddStoreDialogInterface,
        StoreListAdapter.StoreItemInterface {

    private static final String TAG = StoreListActivity.class.getSimpleName();
    RecyclerView mRecyclerView;
    StoreListAdapter mStoreAdapter;
    private StoreListViewModel storeListViewModel;
    private List<Store> mStores = new ArrayList<>();
    private String storeOwnerName;
    private Long storeOwnerId;
    private ActivityStoreListBinding storeListBinding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeListBinding = DataBindingUtil.setContentView(this,R.layout.activity_store_list);
        AndroidThreeTen.init(this);
        //handle intent
        handleIntentArgs();
        storeListBinding.storeListToolbar.setTitle(storeOwnerName);
        setSupportActionBar(storeListBinding.storeListToolbar);
        //retrieve view model
        storeListViewModel = new ViewModelProvider(this).get(StoreListViewModel.class);
        initRecyclerView();

        storeListBinding.addStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show add dialog fragment to create a new store
                Toast.makeText(StoreListActivity.this, "create new store", Toast.LENGTH_SHORT).show();
                AddStoreDialogFragment addStoreDialogFragment = AddStoreDialogFragment.getInstance();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                addStoreDialogFragment.show(ft, "add_dialog_fragment");
            }
        });
    }

    private void handleIntentArgs() {
        //get the store details
        storeOwnerName = getIntent().getStringExtra(STORE_OWNER_NAME);
        storeOwnerId = getIntent().getLongExtra(STORE_OWNER_ID,0);
    }

    private void displayStoreItems() {
       compositeDisposable.add( storeListViewModel.getStoreList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<List<Store>>() {
                    @Override
                    public void onNext(List<Store> stores) {
                       mStoreAdapter.setStoreListItems(stores);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG , "error getting owners " + t.getLocalizedMessage());

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG , "emitted successfully");
                    }
                })
       );
    }

    private void initRecyclerView() {
        //initialize variables
        mRecyclerView = findViewById(R.id.store_list_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStoreAdapter = new StoreListAdapter(StoreListActivity.this, mStores);
        mRecyclerView.setAdapter(mStoreAdapter);
    }


    /*
     * gets the store name from the dialog and save in the database
     * @param storeName - the name of the store to be saved
     */
    @Override
    public void createStore(String storeName) {
        //get the current date
        String currentTime = LocalTime.now().format(org.threeten.bp.format.DateTimeFormatter.ofPattern("HH:mm"));
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String createdAt = currentDate + " " + currentTime;
        //create new store
        storeListViewModel.createNewStore(new Store(storeName,createdAt,storeOwnerId) );

    }

    /*
     * when a store item is clicked
     * @param id - id of the store clicked
     * @param storeName - store name of the store clicked
     */
    @Override
    public void onStoreItemClicked(Store store) {
        Intent intent = new Intent(this, StoreProductActivity.class);
        intent.putExtra(STORE_ID, store.getId());
        intent.putExtra(STORE_NAME, store.getStoreName());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.user_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int position = item.getItemId();

        switch (position) {
            case R.id.add_store:
                //show add dialog fragment to create a new store
                Toast.makeText(this, "create new store", Toast.LENGTH_SHORT).show();
                AddStoreDialogFragment addStoreDialogFragment = AddStoreDialogFragment.getInstance();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                addStoreDialogFragment.show(ft, "add_dialog_fragment");
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //display items
        displayStoreItems();
    }
}

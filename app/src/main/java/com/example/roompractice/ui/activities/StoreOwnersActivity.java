package com.example.roompractice.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.roompractice.R;
import com.example.roompractice.databinding.ActivityStoreOwnersBinding;
import com.example.roompractice.model.Owner;
import com.example.roompractice.ui.adapter.StoreOwnerAdapter;
import com.example.roompractice.ui.fragments.AddStoreOwnerDialogFragment;
import com.example.roompractice.ui.viewmodels.StoreOwnerViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

import static com.example.roompractice.data.Constant.STORE_OWNER_ID;
import static com.example.roompractice.data.Constant.STORE_OWNER_NAME;

public class StoreOwnersActivity extends AppCompatActivity implements
        AddStoreOwnerDialogFragment.AddStoreOwnerDialogInterface ,
        StoreOwnerAdapter.StoreOwnerInterface {

    public static final String TAG = StoreOwnersActivity.class.getSimpleName();
    private StoreOwnerAdapter.StoreOwnerInterface storeOwnerInterface = StoreOwnersActivity.this;
    private ActivityStoreOwnersBinding mStoreownerBinding;
    private StoreOwnerAdapter ownerAdapter;
    public static final String STORE_OWNER_DIALOG_FRAGMENT_TAG = "add_store_owner_dialog";
    private StoreOwnerViewModel ownerViewModel;
    private List<Owner> mItems = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStoreownerBinding = DataBindingUtil.setContentView(this, R.layout.activity_store_owners);
        setSupportActionBar(mStoreownerBinding.storeOwnerToolbar);
        //get the view model
        ownerViewModel = new ViewModelProvider(this).get(StoreOwnerViewModel.class);
        //initialize recyclerview
        initializeRecyclerView();

        mStoreownerBinding.addStoreOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show dialog to create new  store owner
                createStoreOwnerDialog();
            }
        });

        displayItems();
    }

    private void displayItems() {

    }

    private void initializeRecyclerView() {

        mStoreownerBinding.storeOwnersListRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        ownerAdapter = new StoreOwnerAdapter(this,mItems ,storeOwnerInterface);
        mStoreownerBinding.storeOwnersListRecyclerview.setAdapter(ownerAdapter);
    }

    private void createStoreOwnerDialog() {
        AddStoreOwnerDialogFragment fragmentDialog = AddStoreOwnerDialogFragment.getInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        fragmentDialog.show(ft, STORE_OWNER_DIALOG_FRAGMENT_TAG);

    }

    @Override
    public void createStoreOwner(String ownerLevel, String ownerName) {
        //insert to database
        Toast.makeText(this, " " + ownerName + " " + ownerLevel, Toast.LENGTH_SHORT).show();
        ownerViewModel.createNewStoreOwner(new Owner(ownerName,ownerLevel));

    }

    @Override
    protected void onResume() {
        super.onResume();
        //get all owners
        compositeDisposable.add( ownerViewModel.getAllOwners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<List<Owner>>() {
                    @Override
                    public void onNext(List<Owner> owners) {
                      //refresh items
                      ownerAdapter.setItems(owners);
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

    @Override
    public void onItemClicked(Owner owner) {
       Intent intent = new Intent(StoreOwnersActivity.this ,StoreListActivity.class);
       intent.putExtra(STORE_OWNER_ID, owner.getId());
       intent.putExtra(STORE_OWNER_NAME, owner.getUserName());
       startActivity(intent);
    }
}

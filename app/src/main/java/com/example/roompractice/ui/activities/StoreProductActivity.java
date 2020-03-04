package com.example.roompractice.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roompractice.R;
import com.example.roompractice.data.StoreListDatabase;
import com.example.roompractice.databinding.ActivityStoreProductBinding;
import com.example.roompractice.model.Product;
import com.example.roompractice.ui.adapter.ProductListAdapter;
import com.example.roompractice.ui.fragments.AddProductDialogFragment;
import com.example.roompractice.ui.viewmodels.StoreProductsViewModel;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import static com.example.roompractice.data.Constant.STORE_ID;
import static com.example.roompractice.data.Constant.STORE_NAME;


public class StoreProductActivity extends AppCompatActivity implements AddProductDialogFragment.AddProductDialogInterface
        , ProductListAdapter.StoreProductInterface{

    private static final String TAG = StoreProductActivity.class.getSimpleName();
    RecyclerView mRecyclerView;
    ProductListAdapter productListAdapter;
    private List<Product> mStoreItems = new ArrayList<>();
    private ActivityStoreProductBinding productBinding;
    private StoreProductsViewModel storeProductsViewModel;
    Toolbar mToobar;
    Long mStoreId;
    String mStoreName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productBinding = DataBindingUtil.setContentView(this, R.layout.activity_store_product);
        //get passed items
        handleIntent();
        //initialize views
        productBinding.storeProductToolbar.setTitle(mStoreName);
        setSupportActionBar(productBinding.storeProductToolbar);
        //initialize recycler view
        initRecyclerView();
        //retrieve view model
        storeProductsViewModel = new ViewModelProvider(this).get(StoreProductsViewModel.class);
        //display products in the store
        displayProductItems();

    }

    private void handleIntent() {
        mStoreId = getIntent().getLongExtra(STORE_ID, 0);
        mStoreName = getIntent().getStringExtra(STORE_NAME);
        Log.d(TAG, "store id : " + mStoreId);
        Log.d(TAG, "store name : " + mStoreName);
    }

    private void displayProductItems() {
        //retrieve view model
        storeProductsViewModel = new ViewModelProvider(this).get(StoreProductsViewModel.class);

        storeProductsViewModel.getProductsInStore(mStoreId).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productListAdapter.setProductItems(products);
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.products_in_store_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productListAdapter = new ProductListAdapter(this , storeProductsViewModel.storeProductsRepository.getProductsInStore(mStoreId).getValue());
        mRecyclerView.setAdapter(productListAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.user_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_store) {//add product to store via store product dialog
            Toast.makeText(this, "add product ", Toast.LENGTH_SHORT).show();
            AddProductDialogFragment dialogFragment = AddProductDialogFragment.getInstance();
            Bundle args = new Bundle();
            /*args.putInt(STORE_ID, mStoreId);*/
            dialogFragment.setArguments(args);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            dialogFragment.show(fragmentTransaction, "add_product");
        }
        return true;
    }

    /*
     * when the "add product" dialog button is clicked
     */
    @Override
    public void addProduct(int productCount, String productName) {
        //get the current date
        String currentTime = LocalTime.now().format(org.threeten.bp.format.DateTimeFormatter.ofPattern("HH:mm"));
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String createdAt = currentDate + " " + currentTime;
        //new product item
        Product item = new Product(productName,productCount,createdAt);
        //add product to store
        storeProductsViewModel.addProductToStore(mStoreId, item);
    }

    @Override
    public void onProductItemClicked(Product product) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //get the products
        //storeProductsViewModel.getProductsInStoreItems();

    }
}

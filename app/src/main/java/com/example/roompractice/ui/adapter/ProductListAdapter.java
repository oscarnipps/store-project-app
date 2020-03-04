package com.example.roompractice.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roompractice.R;
import com.example.roompractice.databinding.ProductItemBinding;
import com.example.roompractice.model.Product;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.StoreProductViewHolder> {

    private Context mContext;
    private List<Product> mStoreProducts;
    private StoreProductInterface mInterface;
    private ProductItemBinding productItemBinding;

    public void setProductItems(List<Product> products) {
        mStoreProducts = products;
        notifyDataSetChanged();
    }

    public interface StoreProductInterface {
        void onProductItemClicked(Product product);
    }


    public ProductListAdapter(Context context , List<Product> storeProducts ) {
        this.mContext = context;
        this.mStoreProducts = storeProducts;
        this.mInterface = (StoreProductInterface) context;
    }

    @NonNull
    @Override
    public StoreProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        productItemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), (R.layout.product_item ), parent ,false);
        return new StoreProductViewHolder(productItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreProductViewHolder holder, int position) {
        holder.productItemBinding.productName.setText(mStoreProducts.get(position).getProductName());
        String productCountString = mStoreProducts.get(position).getProductCount() + " products";
        holder.productItemBinding.productName.setText(productCountString);
    }

    @Override
    public int getItemCount() {
        if (mStoreProducts == null || mStoreProducts.size() == 0) {
            return 0;
        } else {
            return mStoreProducts.size();
        }
    }


    public class StoreProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ProductItemBinding productItemBinding;

        public StoreProductViewHolder(@NonNull ProductItemBinding binding) {
            super(binding.getRoot());
            this.productItemBinding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //
            int position = getAdapterPosition();
            mInterface.onProductItemClicked(mStoreProducts.get(position));

        }
    }
}

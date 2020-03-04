package com.example.roompractice.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roompractice.R;
import com.example.roompractice.databinding.StoreItemBinding;
import com.example.roompractice.model.Store;

import java.util.List;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.StoreListViewHolder> {

    private Context mContext;
    private List<Store> mStoreItems;
    private StoreItemInterface mInterface;
    private StoreItemBinding storeItemBinding;

    public StoreListAdapter(Context context, List<Store> storeItems) {
        this.mContext = context;
        this.mStoreItems = storeItems;
        this.mInterface = (StoreItemInterface) context;
    }

    public void setStoreListItems(List<Store> stores) {
        mStoreItems = stores;
        notifyDataSetChanged();
    }

    public interface StoreItemInterface {

        //when the store item is clicked
        void onStoreItemClicked(Store store );

    }

    @NonNull
    @Override
    public StoreListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        storeItemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.store_item, parent, false);
        return new StoreListViewHolder(storeItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreListViewHolder holder, int position) {
        holder.storeItemBinding.storeName.setText(mStoreItems.get(position).getStoreName());
        holder.storeItemBinding.storeDate.setText(mStoreItems.get(position).getCreatedAt());
    }

    @Override
    public int getItemCount() {
        if (mStoreItems == null || mStoreItems.size() == 0) {
            return 0;
        } else {
            return mStoreItems.size();
        }
    }

    public class StoreListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        StoreItemBinding storeItemBinding;

        public StoreListViewHolder(@NonNull StoreItemBinding storeItemBinding) {
            super(storeItemBinding.getRoot());
            this.storeItemBinding = storeItemBinding;
            storeItemBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mInterface.onStoreItemClicked(mStoreItems.get(position));

        }
    }

}

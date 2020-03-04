package com.example.roompractice.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roompractice.R;
import com.example.roompractice.databinding.StoreOwnersBinding;
import com.example.roompractice.model.Owner;

import java.util.List;

public class StoreOwnerAdapter extends RecyclerView.Adapter<StoreOwnerAdapter.StoreOwnerViewHolder>{

    private StoreOwnersBinding storeOwnersBinding;
    private Context mContext;
    private List<Owner> mItems;
    private LayoutInflater layoutInflater;
    private StoreOwnerInterface mInterface;


    public StoreOwnerAdapter(Context mContext, List<Owner> mItems, StoreOwnerInterface storeOwnerInterface) {
        this.mContext = mContext;
        this.mItems = mItems;
        this.mInterface = storeOwnerInterface;
    }

    public interface StoreOwnerInterface {

        void onItemClicked(Owner owner);
    }

    @NonNull
    @Override
    public StoreOwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        storeOwnersBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext) ,R.layout.store_owners, parent, false);
        return new StoreOwnerViewHolder(storeOwnersBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull StoreOwnerViewHolder holder, int position) {
        holder.itemBinding.storeOwnerName.setText(mItems.get(position).getUserName());
        holder.itemBinding.storeLevel.setText(mItems.get(position).getLevel());
    }

    @Override
    public int getItemCount() {
        if (mItems == null || mItems.size() == 0) {
            return 0;
        } else {
            return mItems.size();
        }

    }

    public void setItems(List<Owner> items) {
        this.mItems = items;
        //refresh items
        notifyDataSetChanged();
    }

    public class StoreOwnerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private StoreOwnersBinding itemBinding;

        public StoreOwnerViewHolder(@NonNull StoreOwnersBinding binding) {
            super(binding.getRoot());
            this.itemBinding = binding;
            //set click listener
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            String name = mItems.get(position).getUserName();
            String level = mItems.get(position).getLevel();
            mInterface.onItemClicked(mItems.get(position));
        }
    }
}

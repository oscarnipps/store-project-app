package com.example.roompractice.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.roompractice.R;
import com.example.roompractice.databinding.AddStoreOwnerDialogBinding;

public class AddStoreOwnerDialogFragment extends DialogFragment {

    private AddStoreOwnerDialogBinding mStoreOwnerBinding;
    private AddStoreOwnerDialogInterface mInterfaceListener;

    public static AddStoreOwnerDialogFragment getInstance() {
        return new AddStoreOwnerDialogFragment();
    }

    public interface AddStoreOwnerDialogInterface {
        //create new store owner
        void createStoreOwner( String ownerLevel ,String name );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mStoreOwnerBinding = DataBindingUtil.inflate(inflater ,R.layout.add_store_owner_dialog ,container,false);

        //when the the finish button in the view is created
        mStoreOwnerBinding.createStoreOwnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String storeLevel = mStoreOwnerBinding.storeOwnerLevelEditText.getText().toString();
                String ownerName = mStoreOwnerBinding.storeOwnerNameEditText.getText().toString();
                //call method in calling activity
                mInterfaceListener.createStoreOwner(storeLevel,ownerName);
                dismiss();
            }
        });
        return mStoreOwnerBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mInterfaceListener = (AddStoreOwnerDialogInterface) context;
        } catch (Exception e) {
            e.getMessage();
        }
    }
}

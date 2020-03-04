package com.example.roompractice.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.roompractice.R;
import com.example.roompractice.model.Store;

import java.util.List;

public class AddProductDialogFragment extends DialogFragment  {

    public static final String TAG = AddProductDialogFragment.class.getSimpleName();
    private Context mContext;
    public List<Store> mStoreList;
    private AddProductDialogInterface mInterfaceListener;
    private Button addProductDialogButton;
    private EditText productNameEditText, productCountEditText;

    //empty constructor
    public AddProductDialogFragment() {
    }


    //interface for the added products
    public interface AddProductDialogInterface {
        void addProduct(int productCount, String storeName);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static AddProductDialogFragment getInstance() {
        return new AddProductDialogFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_product_dialog, container, false);
        addProductDialogButton = view.findViewById(R.id.create_store_owner_btn);
        productNameEditText = view.findViewById(R.id.store_owner_name_edit_text);
        productCountEditText = view.findViewById(R.id.store_owner_level_edit_text);


        addProductDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = productNameEditText.getText().toString();
                int productCount = Integer.valueOf( productCountEditText.getText().toString());
                mInterfaceListener.addProduct(productCount , productName);
                dismiss();
            }
        });
        return view;
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
            mInterfaceListener = (AddProductDialogInterface) context;
        } catch (ClassCastException e) {

        }
    }
}

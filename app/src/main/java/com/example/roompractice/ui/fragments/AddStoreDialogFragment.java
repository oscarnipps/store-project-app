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

public class AddStoreDialogFragment extends DialogFragment {
public static final String TAG = AddStoreDialogFragment.class.getSimpleName();

    private Context mContext;
    public List<Store> mStoreList;
    private AddStoreDialogInterface mInterfaceListener;
    private Button addStoreDialogButton;
    private EditText storeNameEditText;

    //interface for interaction with the dialog
    public interface AddStoreDialogInterface {
        void createStore(String storeName);
    }

    public AddStoreDialogFragment() { }

    public static AddStoreDialogFragment getInstance() {

        return new AddStoreDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_store_dialog, container, false);

        addStoreDialogButton = view.findViewById(R.id.dialog_add_store);
        storeNameEditText = view.findViewById(R.id.dialog_store_name);

        addStoreDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create the store in the database
                String storeName = storeNameEditText.getText().toString();
                mInterfaceListener.createStore(storeName);
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
            mInterfaceListener = (AddStoreDialogInterface) context;
        } catch (Exception e) {
            e.getMessage();
        }
    }
}

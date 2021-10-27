package com.example.newtonscoaching.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.newtonscoaching.R;
import com.example.newtonscoaching.storage.SharedPref;

public class HomeFrag extends Fragment {
    private TextView textViewEmail, textViewName, textViewContact;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewName = view.findViewById(R.id.textViewName);
        textViewContact = view.findViewById(R.id.textViewContact);

        textViewEmail.setText(SharedPref.getInstance(getActivity()).getUser().getEmail());
        textViewName.setText(SharedPref.getInstance(getActivity()).getUser().getName());
        textViewContact.setText(SharedPref.getInstance(getActivity()).getUser().getContact());
    }
}

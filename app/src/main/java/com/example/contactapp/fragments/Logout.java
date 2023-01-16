package com.example.contactapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.contactapp.R;
import com.google.firebase.auth.FirebaseAuth;


public class Logout extends Fragment {

    View root;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.fragment_logout, container, false);
        mAuth=FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent=new Intent(getContext(), com.example.contactapp.ui.activity.SplashActivity.class);
        startActivity(intent);
        return root;
    }
}
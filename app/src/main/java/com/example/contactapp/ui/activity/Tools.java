package com.example.contactapp.ui.activity;

import android.content.Context;
import android.widget.Toast;

public class Tools {
    Boolean response;
    public static Context context;
    public static void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean validator(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
//            showMessage("Required email or password");

            return false;
        }
        return true;
    }
}

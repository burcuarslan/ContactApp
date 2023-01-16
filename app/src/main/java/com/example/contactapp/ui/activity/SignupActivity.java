package com.example.contactapp.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.contactapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    Button signupButton, loginButton;
    EditText emailText, passwordText;
    FirebaseAuth firebaseAuth;
    String email,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupButton=findViewById(R.id.signup_button_signup);
        firebaseAuth=FirebaseAuth.getInstance();
        loginButton=findViewById(R.id.signup_button_login);
        emailText=findViewById(R.id.signup_email);
        passwordText=findViewById(R.id.signup_password);



        signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                email=emailText.getText().toString().trim();
                password=passwordText.getText().toString().trim();
               if(Tools.validator(email,password)){
                   signupUser();
               }
               else{
                   Tools.showMessage("Required email or password");
               }
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });


    }


    private void signupUser(){
     firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
         @Override
         public void onComplete(@NonNull Task<AuthResult> task) {
             if(task.isSuccessful()){
                 Tools.showMessage("User created successfully");
             }
             else{
                 Tools.showMessage("User creation failed");
             }
         }
     });
    }


}
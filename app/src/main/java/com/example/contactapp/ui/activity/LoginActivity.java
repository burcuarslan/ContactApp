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

public class LoginActivity extends AppCompatActivity {

    Button signupButton ,loginButton;
    EditText emailText,passwordText;
    FirebaseAuth firebaseAuth;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();
        signupButton=findViewById(R.id.login_button_signup);
        loginButton=findViewById(R.id.login_button_login);
        emailText=findViewById(R.id.login_email);
        passwordText=findViewById(R.id.login_password);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=emailText.getText().toString().trim();
                password=passwordText.getText().toString().trim();

                if(Tools.validator(email,password)){
                    loginUser();

                }
                else{
                    Tools.showMessage(email);
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });


    }

    private void loginUser(){

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Tools.showMessage("User logged in successfully");
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
                else{
                    Tools.showMessage("User login failed");
                }
            }
        });
    }
}
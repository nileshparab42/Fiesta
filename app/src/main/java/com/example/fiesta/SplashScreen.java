package com.example.fiesta;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentUser != null) {
                    String email = currentUser.getEmail();
                    String uid = currentUser.getUid();
                    // do something with the email and uid
                    Intent iHome = new Intent(SplashScreen.this,MainScreen.class);
                    startActivity(iHome);
                } else {
                    // user is not logged in
                    Intent iOnboard = new Intent(SplashScreen.this,OnBoarding.class);
                    startActivity(iOnboard);
                }
            }
        },3000);
    }
}
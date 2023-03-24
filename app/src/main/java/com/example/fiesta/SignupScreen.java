package com.example.fiesta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignupScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
    }

    public void onClickButton(View v) {
        Intent iHome = new Intent(SignupScreen.this,MainScreen.class);
        startActivity(iHome);
    }
}
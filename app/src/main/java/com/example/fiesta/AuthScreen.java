package com.example.fiesta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class AuthScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_screen);

        Button button = (Button)findViewById(R.id.button);
        Button button2 = (Button)findViewById(R.id.button2);


    }

    // Implement the OnClickListener callback
    public void onClickButton1(View v) {
        Intent iHome = new Intent(AuthScreen.this,LoginScreen.class);
        startActivity(iHome);
    }

    public void onClickButton2(View v) {
        Intent iHome = new Intent(AuthScreen.this,SignupScreen.class);
        startActivity(iHome);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
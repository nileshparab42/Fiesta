package com.example.fiesta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Event extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
    }

    public void onClickPay(View view) {
        Intent iTic = new Intent(Event.this,Ticket.class);
        startActivity(iTic);
    }
}


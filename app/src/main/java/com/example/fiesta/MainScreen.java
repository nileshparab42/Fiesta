package com.example.fiesta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.sax.RootElement;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.fiesta.databinding.ActivityMainBinding;
import com.example.fiesta.databinding.MainScreenBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainScreen extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    MainScreenBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = MainScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FragmentManager homeMan = getSupportFragmentManager();

        FragmentTransaction homeTrans = homeMan.beginTransaction();

        homeTrans.replace(R.id.content,new Home());
//        homeTrans.addToBackStack(null);
        homeTrans.commit();

        binding.bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager homeMan = getSupportFragmentManager();
                FragmentTransaction homeTrans = homeMan.beginTransaction();
//                homeTrans.addToBackStack(null);
                switch (item.getItemId()){
                    case R.id.home:
                        homeTrans.replace(R.id.content,new Home());
                        homeTrans.commit();
//                        Toast.makeText(MainScreen.this, "Home Selected", Toast.LENGTH_SHORT).show();
                        break;
                }
                switch (item.getItemId()){
                    case R.id.group:
                        homeTrans.replace(R.id.content,new Groups());
                        homeTrans.commit();

//                        Toast.makeText(MainScreen.this, "Group Selected", Toast.LENGTH_SHORT).show();
                        break;
                }
                switch (item.getItemId()){
                    case R.id.table:
                        homeTrans.replace(R.id.content,new Tables());
                        homeTrans.commit();
//                        Toast.makeText(MainScreen.this, "Table Selected", Toast.LENGTH_SHORT).show();
                        break;
                }
                switch (item.getItemId()){
                    case R.id.profile:
                        homeTrans.replace(R.id.content,new Profile());
                        homeTrans.commit();
//                        Toast.makeText(MainScreen.this, "Account Selected", Toast.LENGTH_SHORT).show();
                        break;
                }

                return true;
            }
        });
    }

//    public void onClickCom(View v) {
//        Intent com = new Intent(MainScreen.this,Group.class);
//        startActivity(com);
//    }

    public void onClickReadme(View view) {
        Intent iEve = new Intent(MainScreen.this,Event.class);
        startActivity(iEve);
    }

    public void onClickPay(View view) {
        Intent iTic = new Intent(MainScreen.this,Ticket.class);
        startActivity(iTic);
    }
}


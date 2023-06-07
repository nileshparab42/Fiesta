package com.example.fiesta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.sax.RootElement;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.fiesta.databinding.ActivityMainBinding;
import com.example.fiesta.databinding.MainScreenBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainScreen extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    
    
    MainScreenBinding binding;

    final int UPI_PAYMENT = 0;


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

    public void onClickReadme420(View view) {
        Intent iEve = new Intent(MainScreen.this,PocketActivity.class);
        startActivity(iEve);
    }

    public void onClickPay(View view) {

        String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
        int GOOGLE_PAY_REQUEST_CODE = 123;
        Uri uri =
                new Uri.Builder()
                        .scheme("upi")
                        .authority("pay")
                        .appendQueryParameter("pa", "nileshparab5623@oksbi")
                        .appendQueryParameter("pn", "Nilesh Parab")
//                        .appendQueryParameter("mc", "your-merchant-code")
//                        .appendQueryParameter("tr", "your-transaction-ref-id")
//                        .appendQueryParameter("tn", "your-transaction-note")
                        .appendQueryParameter("am", "5")
                        .appendQueryParameter("cu", "INR")
//                        .appendQueryParameter("url", "your-transaction-url")
                        .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);

        Intent chooser = Intent.createChooser(intent,"Pay with");

        if(null != chooser.resolveActivity(getPackageManager())){
            startActivityForResult(chooser,UPI_PAYMENT);
        } else {
            Toast.makeText(this, "No UPI App Found", Toast.LENGTH_SHORT).show();
        }

    }

    public void goToFlash(View view) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
        Intent iSplash = new Intent(MainScreen.this,SplashScreen.class);
        startActivity(iSplash);
    }

    private void goToPocket(View view) {
        Intent iPocket = new Intent(MainScreen.this,PocketActivity.class);
        startActivity(iPocket);
    }

    private void goToPocket2(View view) {
        Intent iPocket = new Intent(MainScreen.this,PocketActivity.class);
        startActivity(iPocket);

    }
    public void goToPocket3(View view) {
        Intent iEve = new Intent(MainScreen.this, PocketActivity.class);
        startActivity(iEve);
    }

    public void onTTClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String filepath="https://firebasestorage.googleapis.com/v0/b/fiesta-36c5b.appspot.com/o/Timetables%2FACADEMIC%20CALENDAR.docx%20Jan%202023-24.pdf?alt=media&token=8b2e067d-6750-48b6-b1fa-0d9b050ee986";
        intent.setDataAndType(Uri.parse(filepath), "application/pdf");
        startActivity(intent);


//                Intent intent = new Intent(getContext(),PdfActivity.class);
////                tid = arrPreticket.get(position).getTid();
////                intent.putExtra("tid",tid);
//                startActivity(intent);
    }

    public void onAcClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String filepath="https://firebasestorage.googleapis.com/v0/b/fiesta-36c5b.appspot.com/o/Academics%2Fe15302e6-c51f-44ff-a9e6-0d2e95389d07.pdf?alt=media&token=c279f89c-3c63-42ab-b9c0-47d7119a939d";
        intent.setDataAndType(Uri.parse(filepath), "application/pdf");
        startActivity(intent);


//                Intent intent = new Intent(getContext(),PdfActivity.class);
////                tid = arrPreticket.get(position).getTid();
////                intent.putExtra("tid",tid);
//                startActivity(intent);
    }
}


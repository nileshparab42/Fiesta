package com.example.fiesta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PaymentGateway extends AppCompatActivity implements PaymentResultListener {

    String ename;

    String uname,uemail,tid,img,price,uid;

    TextView textMsg;

    FirebaseFirestore db,dw;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        textMsg = findViewById(R.id.msg);

        ename = getIntent().getStringExtra("ename");

        int money;
        String fmoney;
        price = getIntent().getStringExtra("price");
        price = price.replaceAll("\\s", "");

        money = Integer.parseInt(price);
        money = money * 100;
        fmoney = String.valueOf(money);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        db = FirebaseFirestore.getInstance();
        uid=mAuth.getCurrentUser().getUid();

        tid = uid.substring(0,5)+currentDateandTime;


        Checkout co = new Checkout();

        JSONObject object = new JSONObject();

        try {
            object.put("name","Fiesta");
            object.put("description","The Event Management App");
            object.put("image","https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhVztmYgh4yFOkJeWu-VVJvJ6nlAoPGrp_yHtMR3HofAm1tggX7pdNz3IdtvBu8g2lIeKs1YzyFxciMVWhPlkz63h-4T6np_eUhthmvv5rer4IvUqSuwqNP939HSG8e3Kw70-rXnhHqXf6q38PLoiile3dHH_nYAs6FRIag9xp1lypPp9An3Jq8d9tS7g/s320/F%20(4).png");
            object.put("currency","INR");
            object.put("amount",fmoney);

            JSONObject prefill = new JSONObject();
            prefill.put("contact","9082286717");
            prefill.put("email","nileshparab5623@gmail.com");
            object.put("prefill",prefill);

            co.open(PaymentGateway.this,object);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(PaymentGateway.this, ename, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PaymentGateway.this,RegisterUser.class);
        intent.putExtra("tid",tid);
        intent.putExtra("ename",ename);
        startActivity(intent);

    }

    private void bookingRegistration() {
        dw.collection("Events")
                .document("03l6EHiZy98vEOttBnEy")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if(documentSnapshot!=null && documentSnapshot.exists()){
                            img = documentSnapshot.getString("img");
                            textMsg.setText(img);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PaymentGateway.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(PaymentGateway.this, "Payment Failed", Toast.LENGTH_SHORT).show();
    }


}
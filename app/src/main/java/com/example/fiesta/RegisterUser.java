package com.example.fiesta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {

    TextView textMsg;

    Date date;


    String tid, ename,img,price,uname,uemail,uphno;

    String ldate,ltime;

    FirebaseFirestore db,dw;

    FirebaseAuth nAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = nAuth.getCurrentUser();

    Map<String, Object> booking = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        String uid = currentUser.getUid();
        textMsg = findViewById(R.id.msg);
        ename = getIntent().getStringExtra("ename");
        tid = getIntent().getStringExtra("tid");
        booking.put("ename", ename);
        booking.put("tid", tid);
        String data = ename +" " + tid;
        textMsg.setText(data);


        dw = db.getInstance();


        dw.collection("Events")
                .document(ename)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot!=null && documentSnapshot.exists()){
                                img = documentSnapshot.getString("img");
                                price = documentSnapshot.getString("Price");
                                date = documentSnapshot.getDate("date");

                                DateFormat fdate = new SimpleDateFormat("dd-MMM-yyyy");
                                ldate = fdate.format(date);
                                DateFormat ftime = new SimpleDateFormat("HH:mm:ss");
                                ltime = ftime.format(date);
                                booking.put("date", ldate);
                                booking.put("img", img);
                                booking.put("time", ltime);
                                booking.put("price", price);
//                                textMsg.setText(img);
                                dw.collection("Bookings").document(tid)
                                        .set(booking)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(RegisterUser.this, "Data added succesfully", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(RegisterUser.this, "Error", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterUser.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });


        booking.put("img", img);
        textMsg.setText(img);
        booking.put("price", price);

        dw.collection("Users")
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot!=null && documentSnapshot.exists()){
//                                ename = documentSnapshot.getString("ename");
//                                textEname.setText(ename);
                                uname = documentSnapshot.getString("uname");
                                uemail = documentSnapshot.getString("uemail");
                                uphno = documentSnapshot.getString("phno");
                                booking.put("uname", uname);
                                booking.put("uphno", uphno);
                                booking.put("uemail", uemail);
                                dw.collection("Bookings").document(tid)
                                        .set(booking)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
//                                                Toast.makeText(RegisterUser.this, "Data added succesfully", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
//                                                Toast.makeText(RegisterUser.this, "Error", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterUser.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

        Intent intent = new Intent(RegisterUser.this,PocketActivity.class);
        startActivity(intent);


    }
}
package com.example.fiesta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Ticket extends AppCompatActivity {

    TextView textEname,textUname,textUphno,textUemail,textPrice,textTime,textDate;

    FirebaseFirestore db,dw,ab;

    String edate,etime;
    ImageView imgEve;

    String ename,uname,uemail,uphno,img,price;
    FirebaseAuth mAuth;
    String date,time;

    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout dataLayout;

//    FirebaseUser currentUser = mAuth.getCurrentUser();


    FirebaseAuth nAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = nAuth.getCurrentUser();

    Map<String, Object> booking = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);


        shimmerFrameLayout = findViewById(R.id.shimmer_view);
        dataLayout = findViewById(R.id.data_view);

        dataLayout.setVisibility(View.INVISIBLE);
        shimmerFrameLayout.startShimmer();

        imgEve = findViewById(R.id.imgParty);

        String uid = currentUser.getUid();

//        String uid = currentUser.getUid();

        String tid = getIntent().getStringExtra("tid");
        textEname = findViewById(R.id.enameParty);
        textUemail = findViewById(R.id.uemailParty);
        textUname = findViewById(R.id.unameParty);
        textUphno = findViewById(R.id.uphnoParty);
        textPrice = findViewById(R.id.textPriceParty);
        textTime = findViewById(R.id.textTimeParty);
        textDate = findViewById(R.id.textDateParty);

        dw = db.getInstance();
        ab = db.getInstance();



        dw.collection("Bookings")
                .document(tid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot!=null && documentSnapshot.exists()){
                                ename = documentSnapshot.getString("ename");
                                textEname.setText(ename);
                                img = documentSnapshot.getString("img");
                                Picasso.get().load(img).into(imgEve);
                                price = documentSnapshot.getString("price");
                                price = "Price: Rs. "+price;
                                textPrice.setText(price);
                                date = documentSnapshot.getString("date");
                                date = "Date: "+date;
                                textDate.setText(date);
                                time = documentSnapshot.getString("time");
                                time = "Time: "+time;
                                textTime.setText(time);
                                dataLayout.setVisibility(View.VISIBLE);
                                shimmerFrameLayout.stopShimmer();
                                shimmerFrameLayout.setVisibility(View.INVISIBLE);

                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Ticket.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

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
                                textUname.setText(uname);
                                uemail = documentSnapshot.getString("uemail");
                                textUemail.setText(uemail);
                                uphno = documentSnapshot.getString("uphno");
                                textUphno.setText(uphno);

                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Ticket.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
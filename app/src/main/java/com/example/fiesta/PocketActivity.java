package com.example.fiesta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PocketActivity extends AppCompatActivity implements RecyclerTicketpreInterface{

    public static final String EXTRA_TEXT = "com.example.fiesta.EXTRA_TEXT";

    RecyclerView recyclerView;

    String trimtid,tid;
    LinearLayoutManager linearLayoutManager;

    RecyclerTicketpreAdapter recyclerTicketpreAdapter;
    FirebaseFirestore db;
    String uemail;

    ArrayList<PreticketModel> arrPreticket = new ArrayList<>();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocket);

        db = FirebaseFirestore.getInstance();
        uemail=mAuth.getCurrentUser().getEmail();

        recyclerView = findViewById(R.id.RecyclerTicketpre);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerTicketpreAdapter = new RecyclerTicketpreAdapter(this,arrPreticket,this);

        CollectionReference usersRef = db.collection("Events");

        PreticketChangeListner(recyclerView);

        recyclerView.setAdapter(recyclerTicketpreAdapter);

    }

    private void PreticketChangeListner(RecyclerView recyclerView) {
        db.collection("Bookings")
                .whereEqualTo("uemail",uemail)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error!=null){
                            Log.d("Firestore error", error.getMessage());
                            return;
                        }
                        for(DocumentChange dc:value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                arrPreticket.add(dc.getDocument().toObject(PreticketModel.class));
                            }
                            recyclerTicketpreAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onTktbtnClick(int position) {
        Intent intent = new Intent(PocketActivity.this,Ticket.class);
        tid = arrPreticket.get(position).getTid();
        intent.putExtra("tid",tid);
        startActivity(intent);
    }

    public void onBackPressed() {
        Intent intent = new Intent(PocketActivity.this,MainScreen.class);
        startActivity(intent);
    }
}


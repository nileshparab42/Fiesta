package com.example.fiesta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Group extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    ArrayList<GroupModel> arrGroups = new ArrayList<>();

    RecyclerGroupAdapter recyclerGroupAdapter;

    FirebaseFirestore db;

    String gname,title;

    TextView ivGname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        gname = getIntent().getStringExtra("gname");
        title = getIntent().getStringExtra("title");

        ivGname=findViewById(R.id.tvGname);

        ivGname.setText(title);



        recyclerView = findViewById(R.id.RecyclerGroup);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        db=FirebaseFirestore.getInstance();

        recyclerGroupAdapter = new RecyclerGroupAdapter(this,arrGroups);

        GroupChangeListner(recyclerView);

        recyclerView.setAdapter(recyclerGroupAdapter);


    }

    private void GroupChangeListner(RecyclerView recyclerView) {
        db.collection("Groups")
                .whereEqualTo("gname",gname)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error!=null){
                            Log.d("Firestore error", error.getMessage());
                            return;
                        }
                        for(DocumentChange dc:value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                arrGroups.add(dc.getDocument().toObject(GroupModel.class));
                            }
                            recyclerGroupAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }

    public void onBackPressed() {
        Intent intent = new Intent(Group.this,MainScreen.class);
        startActivity(intent);
    }

}

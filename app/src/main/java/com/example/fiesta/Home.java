package com.example.fiesta;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.razorpay.PaymentResultListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment implements RecyclerEventInterface, PaymentResultListener {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<EventModel> arrEvent;

    final int UPI_PAYMENT = 0;

    ImageView pktbtn;
    LinearLayoutManager linearLayoutManager;

    StorageReference storageReference;

    RecyclerView recyclerView;

    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout dataLayout;

    RecyclerEventAdapter recyclerEventAdapter;

    FirebaseFirestore db,dw;

    String nickname;

    TextView textNick;


    String img,uid;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    FirebaseAuth nAuth = FirebaseAuth.getInstance();
    ImageView ivPrpic;
    FirebaseUser currentUser = nAuth.getCurrentUser();


    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        shimmerFrameLayout = view.findViewById(R.id.shimmer_view);
        dataLayout =view.findViewById(R.id.data_view);

        dataLayout.setVisibility(View.INVISIBLE);
        shimmerFrameLayout.startShimmer();

        dw = db.getInstance();

        uid = currentUser.getUid();

        ivPrpic = view.findViewById(R.id.prpic);

        pktbtn = view.findViewById(R.id.pktbtn);
        pktbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        textNick = view.findViewById(R.id.nickname);
        dw.collection("Users")
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot!=null && documentSnapshot.exists()){
                                nickname = documentSnapshot.getString("nickname");
                                textNick.setText(nickname);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });


        String fileName = currentUser.getUid();
        storageReference = storage.getReference().child("Profile Photos/"+fileName);

        // Retrieve the image data as a byte array
        storageReference.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Create a Bitmap from the image data
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                        // Display the Bitmap in an ImageView
                        ivPrpic.setImageBitmap(bitmap);
                        dataLayout.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.INVISIBLE);
//                        ivPrpic.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 500, 500, false));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_SHORT).show();
                    }
                });


        recyclerView = view.findViewById(R.id.RecyclerEvents);
        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        db = FirebaseFirestore.getInstance();
//        RecyclerEventInterface recyclerEventInterface = null;
        arrEvent = new ArrayList<EventModel>();
        recyclerEventAdapter = new RecyclerEventAdapter(getContext(),arrEvent, this);

        storageReference = storage.getReference().child("Events/freshers_party.jpg");
        String imgadd = "https://firebasestorage.googleapis.com/v0/b/fiesta-36c5b.appspot.com/o/Events%2Ffreshers_party.jpg?alt=media&token=d39f3d06-93d2-4b4f-b65b-81316770f0e2";

        CollectionReference usersRef = db.collection("Events");

        EventChangeListner();

        recyclerView.setAdapter(recyclerEventAdapter);


        return view;
    }


    private void EventChangeListner() {
        db.collection("Events").orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error!=null){
                            Log.d("Firestore error", error.getMessage());
                            return;
                        }
                        for(DocumentChange dc:value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                arrEvent.add(dc.getDocument().toObject(EventModel.class));
                            }
                            recyclerEventAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }


    @Override
    public void onBtnClick(int position) {
        Intent intent = new Intent(getActivity(),PaymentGateway.class);
        intent.putExtra("price",arrEvent.get(position).getPrice());
        intent.putExtra("ename",arrEvent.get(position).getName());
        startActivity(intent);

        //RazorPay API
//        Checkout co = new Checkout();
//
//        JSONObject object = new JSONObject();
//
//        try {
//            object.put("name","Fiesta");
//            object.put("description","The Event Management App");
//            object.put("image","https://cdn.logo.com/hotlink-ok/logo-social.png");
//            object.put("currency","INR");
//            object.put("amount","100");
//
//            JSONObject prefill = new JSONObject();
//            prefill.put("contact","9082286717");
//            prefill.put("email","nileshparab5623@gmail.com");
//            object.put("prefill",prefill);
//
//            co.open(getActivity(),object);
//
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }


        //GPay API
//        String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
//        int GOOGLE_PAY_REQUEST_CODE = 123;
//        EventModel eventModel;
//        String Price = arrEvent.get(position).getPrice();
//        Price = Price.replaceAll("\\s", "");
//        Uri uri =
//                new Uri.Builder()
//                        .scheme("upi")
//                        .authority("pay")
//                        .appendQueryParameter("pa", "nileshparab5623@oksbi")
//                        .appendQueryParameter("pn", "Nilesh Parab")
////                        .appendQueryParameter("mc", "your-merchant-code")
////                        .appendQueryParameter("tr", "your-transaction-ref-id")
////                        .appendQueryParameter("tn", "your-transaction-note")
//                        .appendQueryParameter("am", Price)
//                        .appendQueryParameter("cu", "INR")
////                        .appendQueryParameter("url", "your-transaction-url")
//                        .build();
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(uri);
//
//        Intent chooser = Intent.createChooser(intent,"Pay with");
//
//        if(null != chooser.resolveActivity(getActivity().getPackageManager())){
//            startActivityForResult(chooser,UPI_PAYMENT);
//        } else {
//            Toast.makeText(getContext(), "No UPI App Found", Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(getContext(), "Payment Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getContext(), "Payment Failed", Toast.LENGTH_SHORT).show();
    }
}
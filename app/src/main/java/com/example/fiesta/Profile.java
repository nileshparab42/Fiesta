package com.example.fiesta;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Uri profilePic;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference;
    ImageView ivProfile;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout dataLayout;

//    ProgressDialog progressdia;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        shimmerFrameLayout = view.findViewById(R.id.profile_ph);
        dataLayout = view.findViewById(R.id.profile_data);
        ivProfile = view.findViewById(R.id.ivProfile);

        dataLayout.setVisibility(View.INVISIBLE);
        shimmerFrameLayout.startShimmer();


        String fileName = currentUser.getUid();
        storageReference = storage.getReference().child("Profile Photos/"+fileName);

        // Retrieve the image data as a byte array
        storageReference.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        dataLayout.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.INVISIBLE);
                        // Create a Bitmap from the image data
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                        // Display the Bitmap in an ImageView
                        ivProfile.setImageBitmap(bitmap);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_SHORT).show();
                    }
                });

        ActivityResultLauncher<String> mTakePhoto;
        mTakePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        profilePic = result;
                        ivProfile.setImageURI(profilePic);
                        uploadToStorage();
                    }
                }
        );

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataLayout.setVisibility(View.INVISIBLE);
                shimmerFrameLayout.startShimmer();
                mTakePhoto.launch("image/*");
            }
        });

        return view;
    }

    private void uploadToStorage() {


        String fileName = currentUser.getUid();

        storageReference = FirebaseStorage.getInstance().getReference("Profile Photos/"+fileName);
        storageReference.putFile(profilePic)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        progressdia.dismiss();
                        ivProfile.setImageURI(null);
                        Toast.makeText(getActivity(), "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                        storageReference = storage.getReference().child("Profile Photos/"+fileName);

                        // Retrieve the image data as a byte array
                        storageReference.getBytes(1024 * 1024)
                                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        dataLayout.setVisibility(View.VISIBLE);
                                        shimmerFrameLayout.stopShimmer();
                                        shimmerFrameLayout.setVisibility(View.INVISIBLE);
                                        // Create a Bitmap from the image data
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                                        // Display the Bitmap in an ImageView
                                        ivProfile.setImageBitmap(bitmap);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        progressdia.dismiss();
                        Toast.makeText(getActivity(), "Failed to Upload", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
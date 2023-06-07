package com.example.fiesta;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class UserInfo extends AppCompatActivity {

    EditText etUname,etNickname, etUbday,etUlocation, etUphno;
    Button submitbtn;

    String uname,nickname,ubday,ulocation,uemail,uphno,uid;

    FirebaseAuth nAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = nAuth.getCurrentUser();

    FirebaseFirestore db,dw;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference;

    Uri profilePic;

    ImageView ivProfile;

    Map<String, Object> user = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ivProfile = findViewById(R.id.ivProfile);

        //================================================================
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
                        ivProfile.setImageBitmap(bitmap);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserInfo.this, "Set Profile Photo", Toast.LENGTH_SHORT).show();
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
                mTakePhoto.launch("image/*");
            }
        });

        //===================================






        dw = db.getInstance();

        uid = currentUser.getUid();
        uemail=currentUser.getEmail();
        user.put("uid",uid);
        user.put("uemail",uemail);

        etUname = findViewById(R.id.uname);
        etNickname = findViewById(R.id.shnickname);
        etUbday = findViewById(R.id.ubday);
        etUlocation = findViewById(R.id.ulocation);
        etUphno = findViewById(R.id.uphno);

        submitbtn = findViewById(R.id.submitbtn);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProfile();
            }
        });


    }

    private void uploadToStorage() {


        String fileName = currentUser.getUid();

        storageReference = FirebaseStorage.getInstance().getReference("Profile Photos/"+fileName);
        storageReference.putFile(profilePic)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                      progressdia.dismiss();
                        ivProfile.setImageURI(null);
                        Toast.makeText(UserInfo.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                        storageReference = storage.getReference().child("Profile Photos/"+fileName);

                        // Retrieve the image data as a byte array
                        storageReference.getBytes(1024 * 1024)
                                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        // Create a Bitmap from the image data
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


                                        // Display the Bitmap in an ImageView
                                        ivProfile.setImageBitmap(bitmap);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(UserInfo.this, "Please Select Lower Quality Image", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        progressdia.dismiss();
                        Toast.makeText(UserInfo.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createProfile() {
        uname=etUname.getText().toString();
        nickname=etNickname.getText().toString();
        ubday=etUbday.getText().toString();
        ulocation=etUlocation.getText().toString();
        uphno=etUphno.getText().toString();
        user.put("uname",uname);
        user.put("nickname",nickname);
        user.put("ubday",ubday);
        user.put("ulocation",ulocation);
        user.put("uphno",uphno);

        dw.collection("Users").document(uid)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UserInfo.this, "Data added succesfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserInfo.this,MainScreen.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserInfo.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
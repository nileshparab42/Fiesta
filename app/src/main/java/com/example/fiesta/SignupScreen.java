package com.example.fiesta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.collection.LLRBNode;

import org.json.JSONObject;

import java.util.AbstractCollection;
import java.util.HashMap;
import java.util.Map;

public class SignupScreen extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {
    EditText editEmail,editPassword,editRepass;

    Button buttonSignup;
    ProgressDialog progressdia;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    TextView googleCaptchaText;

    GoogleApiClient googleApiClient;

    String SITE_KEY = "6Lf73bQlAAAAAPr4cerRWNt6fjnMeDpUhJdtlw7L";

    CheckBox cbCaptcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);



        googleCaptchaText =  findViewById(R.id.googleCaptchaText);
        cbCaptcha =  findViewById(R.id.cbCaptcha);


        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(SignupScreen.this)
                .build();
        googleApiClient.connect();

        cbCaptcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbCaptcha.isChecked()){
                    SafetyNet.SafetyNetApi.verifyWithRecaptcha(googleApiClient,SITE_KEY)
                            .setResultCallback(new ResultCallback<SafetyNetApi.RecaptchaTokenResult>() {
                                @Override
                                public void onResult(@NonNull SafetyNetApi.RecaptchaTokenResult recaptchaTokenResult) {
                                    Status status=recaptchaTokenResult.getStatus();
                                    if((status!=null)&&status.isSuccess()){
                                        Toast.makeText(SignupScreen.this, "Varification Successful", Toast.LENGTH_SHORT).show();
                                        cbCaptcha.setChecked(true);
                                    }

                                }
                            });
                }else{
                    Toast.makeText(SignupScreen.this, "Varification Not Done", Toast.LENGTH_SHORT).show();
                    cbCaptcha.setChecked(false);
                }
            }
        });



        editEmail =  findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editRepass = findViewById(R.id.editRepass);
        buttonSignup =  findViewById(R.id.buttonSignup);
        progressdia = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();



        buttonSignup.setOnClickListener(view -> PerformAuth());

    }

    private void PerformAuth() {
        if(cbCaptcha.isChecked()) {
            String email = editEmail.getText().toString();
            String pass = editPassword.getText().toString();
            String repass = editRepass.getText().toString();

            if (!email.matches(emailPattern)) {
                editEmail.setError("Enter Correct Email");
            } else if (pass.isEmpty() || pass.length() < 6) {
                editPassword.setError("Enter Proper Password");
            } else if (!pass.equals(repass)) {
                editRepass.setError("Password not matching");
            } else {
                progressdia.setMessage("Please wait while registration ...");
                progressdia.setTitle("Registration");
                progressdia.setCanceledOnTouchOutside(false);
                progressdia.show();

                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressdia.dismiss();
                            sendUserToHome();
                            Toast.makeText(SignupScreen.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            progressdia.dismiss();
                            Toast.makeText(SignupScreen.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }else{
            Toast.makeText(this, "Please verify reCaptcha", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendUserToHome() {
            Intent iHome = new Intent(SignupScreen.this,UserInfo.class);
            iHome.setFlags(iHome.FLAG_ACTIVITY_CLEAR_TASK|iHome.FLAG_ACTIVITY_NEW_TASK);
            startActivity(iHome);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
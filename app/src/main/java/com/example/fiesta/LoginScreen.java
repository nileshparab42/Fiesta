package com.example.fiesta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {

    EditText editEmail,editPassword;
    Button buttonLogin;
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
        setContentView(R.layout.activity_login_screen);

        googleCaptchaText =  findViewById(R.id.googleCaptchaText);
        cbCaptcha =  findViewById(R.id.cbCaptcha);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(LoginScreen.this)
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
                                        Toast.makeText(LoginScreen.this, "Varification Successful", Toast.LENGTH_SHORT).show();
                                        cbCaptcha.setChecked(true);
                                    }

                                }
                            });
                }else{
                    Toast.makeText(LoginScreen.this, "Varification Not Done", Toast.LENGTH_SHORT).show();
                    cbCaptcha.setChecked(false);
                }
            }
        });

        editEmail =  findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        buttonLogin =  findViewById(R.id.buttonLogin);
        progressdia = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        buttonLogin.setOnClickListener(view -> PerformLogin());
    }

    private void PerformLogin() {
        if(cbCaptcha.isChecked()) {
            String email = editEmail.getText().toString();
            String pass = editPassword.getText().toString();

            if (!email.matches(emailPattern)) {
                editEmail.setError("Enter Correct Email");
            } else if (pass.isEmpty() || pass.length() < 6) {
                editPassword.setError("Enter Proper Password");
            } else {
                progressdia.setMessage("Please wait while Login ...");
                progressdia.setTitle("Login");
                progressdia.setCanceledOnTouchOutside(false);
                progressdia.show();

                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressdia.dismiss();
                            sendUserToHome();
                            Toast.makeText(LoginScreen.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            progressdia.dismiss();
                            Toast.makeText(LoginScreen.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }else{
            Toast.makeText(this, "Please verify reCaptcha", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendUserToHome() {
        Intent iHome = new Intent(LoginScreen.this,MainScreen.class);
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
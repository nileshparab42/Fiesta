package com.example.fiesta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupScreen extends AppCompatActivity {
    EditText editEmail,editPassword,editRepass;
    Button buttonSignup;
    ProgressDialog progressdia;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

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
        String email = editEmail.getText().toString();
        String pass = editPassword.getText().toString();
        String repass = editRepass.getText().toString();

        if(!email.matches(emailPattern)){
            editEmail.setError("Enter Correct Email");
        } else if (pass.isEmpty() || pass.length()<6) {
            editPassword.setError("Enter Proper Password");
        } else if (!pass.equals(repass)) {
            editRepass.setError("Password not matching");
        }
        else{
            progressdia.setMessage("Please wait while registration ...");
            progressdia.setTitle("Registration");
            progressdia.setCanceledOnTouchOutside(false);
            progressdia.show();

            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressdia.dismiss();
                        sendUserToHome();
                        Toast.makeText(SignupScreen.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }else {
                        progressdia.dismiss();
                        Toast.makeText(SignupScreen.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToHome() {
        Intent iHome = new Intent(SignupScreen.this,MainScreen.class);
        iHome.setFlags(iHome.FLAG_ACTIVITY_CLEAR_TASK|iHome.FLAG_ACTIVITY_NEW_TASK);
        startActivity(iHome);
    }

}
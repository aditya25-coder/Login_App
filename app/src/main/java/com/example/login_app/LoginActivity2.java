package com.example.login_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity2 extends AppCompatActivity {
    public EditText emailId,password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        mFirebaseAuth=FirebaseAuth.getInstance();
        emailId=findViewById(R.id.editTextTextEmailAddress);
        password=findViewById(R.id.editTextTextPassword);
        btnSignIn=findViewById(R.id.button);
        tvSignUp=findViewById(R.id.textView);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(LoginActivity2.this, "You are Logged in ", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity2.this,HomeActivity2.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(LoginActivity2.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = emailId.getText().toString();
                    String pwd = password.getText().toString();
                    if(email.isEmpty()){
                        emailId.setError("Please Enter Email-Id");
                        emailId.requestFocus();
                    }
                    else if(pwd.isEmpty()){
                        password.setError("Please Enter Password");
                        password.requestFocus();
                    }
                    else if(email.isEmpty() && pwd.isEmpty()){
                        Toast.makeText(LoginActivity2.this,"Fields are Empty !!",Toast.LENGTH_SHORT).show();
                    }
                    else if (!(email.isEmpty() && pwd.isEmpty())){
                        mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(LoginActivity2.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(LoginActivity2.this,"Login Error,Please login again.",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Intent intToHome = new Intent(LoginActivity2.this, HomeActivity2.class);
                                    startActivity(intToHome);
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(LoginActivity2.this,"Error Occurred!!",Toast.LENGTH_SHORT).show();
                    }

                }
            });
            tvSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intSignUp = new Intent(LoginActivity2.this, MainActivity.class);
                    startActivity(intSignUp);
                }
            });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}

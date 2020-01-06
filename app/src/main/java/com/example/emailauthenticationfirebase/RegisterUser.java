package com.example.emailauthenticationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {
    FirebaseAuth fauth;
    EditText username,email,password;
    Button register;
    TextView loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        username=findViewById(R.id.ed_username);
        email=findViewById(R.id.ed_email);
        password=findViewById(R.id.ed_password);
        register=findViewById(R.id.register_btn);
        loginButton=findViewById(R.id.goto_login);
        fauth=FirebaseAuth.getInstance();


        if (fauth.getCurrentUser()!= null){
            startActivity(new Intent(RegisterUser.this,MainActivity.class));

        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterUser.this,LoginUser.class));

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String r_name = username.getText().toString().trim();
                String r_email = email.getText().toString().trim();
                String r_pass = password.getText().toString().trim();

                if (TextUtils.isEmpty(r_name)){
                    email.setError("Please Enter Name");
                    return;
                }

                if (TextUtils.isEmpty(r_email)){
                    email.setError("Please Enter Email");
                    return;
                }

                if (TextUtils.isEmpty(r_pass)){
                    email.setError("Please Enter password");
                    return;
                }

                if (r_email.length()<6){
                    email.setError("Please Enter password 6 digit");
                    return;
                }

                fauth.createUserWithEmailAndPassword(r_email,r_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterUser.this, "User create Created", Toast.LENGTH_SHORT).show();
                            // Database Start

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("users");

                            myRef.child(fauth.getCurrentUser().getUid()).setValue(r_name);

                            // Database End
                            startActivity( new Intent(getApplicationContext(),MainActivity.class));

                        }
                        else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(),"User is already Registered",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Error :"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

    }
}

package com.example.emailauthenticationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginUser extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView new_register, Login;
    EditText email,password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        new_register=findViewById(R.id.goto_register);
        Login=findViewById(R.id.Login_btn);
        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.ed_email_login);
        password=findViewById(R.id.ed_password_login);

        if (mAuth.getCurrentUser()!= null){
            startActivity(new Intent(LoginUser.this,MainActivity.class));

        }
        new_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginUser.this,RegisterUser.class));

            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String r_name = username.getText().toString().trim();
                String m_email = email.getText().toString().trim();
                String m_pass = password.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(m_email, m_pass)
                        .addOnCompleteListener(LoginUser.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(LoginUser.this,MainActivity.class));
                                } else {
                                    Toast.makeText(LoginUser.this, "Wrong email or password", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
}

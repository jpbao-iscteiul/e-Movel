package com.example.mycrm_teste;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText mNome, mEmail, mPassword, mConfirmPass;
   private Button mRegisterButton;
    //TextView mLoginButton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mNome = findViewById(R.id.RegiName);
        mEmail = findViewById(R.id.RegiEmail);
        mPassword = findViewById(R.id.RegiPass);
        mConfirmPass = findViewById(R.id.RegiConfirmPass);

        fAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressBar2);

        mRegisterButton = findViewById(R.id.registarButton);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =  mEmail.getText().toString().trim();
                String password =  mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email necessario");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                     mEmail.setError("Password necessaria");
                     return;
                }

                if(password.length() < 6 ){
                    mPassword.setError("Password tem de ter mais de 6 caracteres");
                    return;
                }

                if( ! mPassword.getText().toString().equals(mConfirmPass.getText().toString())){
                    mPassword.setError("Passwords nao correspondem!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                if(fAuth.getCurrentUser() != null){
                    startActivity(new Intent(getApplicationContext(), auth_main.class));
                    finish();
                }

                //Regista utilizador no Firebase!!

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), auth_main.class));
                            progressBar.setVisibility(View.INVISIBLE);

                        }else {
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                   }
                });
            }
        });
    }
}

package com.example.mycrm_teste;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class auth_main extends AppCompatActivity {

    FirebaseAuth fAuth;
    Button buttonLogIn;
    TextView registar;
    EditText mEmail, mPassword;
    ProgressBar progressBar;
    CheckBox remember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_main);

        fAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.email_Text);
        mPassword = findViewById(R.id.pass_Text);
        progressBar = findViewById(R.id.progressBar);
        remember = findViewById(R.id.remember_me);
        registar = findViewById(R.id.textView4);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");

        if(checkbox.equals("true")){
            Intent intent = new Intent(auth_main.this, MainActivity.class);
            startActivity(intent);
        }

        registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(auth_main.this , "Checked", Toast.LENGTH_SHORT).show();
                }else if (!buttonView.isChecked()){
                        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("remember", "false");
                        editor.apply();
                        Toast.makeText(auth_main.this , "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });






        buttonLogIn = findViewById(R.id.BackButtonDash);
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String email = mEmail.getText().toString().trim();
               String password = mPassword.getText().toString().trim();


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

                progressBar.setVisibility(View.VISIBLE);


                //AUTENTICAÇÃO

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(auth_main.this, "Log In Bem Sucedido", Toast.LENGTH_SHORT).show();
                            startActivity( new Intent(getApplicationContext(),MainActivity.class));
                            progressBar.setVisibility(View.INVISIBLE);

                        }else {
                            Toast.makeText(auth_main.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                //if(userConfirmed()){

                //}
            }
        });

    }

    private void openRegisterActivity() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}

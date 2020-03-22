package com.example.mycrm_teste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Script;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class AddClient extends AppCompatActivity {

    private EditText mClienteName;
    private EditText mClienteMail;
    private EditText mClientePhone;
    private EditText mClienteLocation;
    private Spinner mClienteHuseType;
    private Button mAddClienteButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        mClienteName = findViewById(R.id.addClient_name);
        mClienteMail = findViewById(R.id.addClient_mail);
        mClientePhone = findViewById(R.id.addClient_phone);
        mClienteLocation = findViewById(R.id.addClient_location);

        mClienteHuseType = findViewById(R.id.addClient_housetype);

        mAddClienteButton = findViewById(R.id.addClientButton);


        mAddClienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ClientClass cliente = createClient();

               new FbDatabaseHelper().addClient(cliente, new FbDatabaseHelper.DataStatus() {
                   @Override
                   public void DataIsLoaded(List<ClientClass> clients, List<String> keys) {

                   }

                   @Override
                   public void DataIsInserted() {
                       Toast.makeText(AddClient.this, "New Client has been inserted", Toast.LENGTH_LONG).show();
                       finish();
                   }

                   @Override
                   public void DataIsUpdated() {

                   }

                   @Override
                   public void DataIsDeleted() {

                   }
               });

            }
        });
    }


    public ClientClass  createClient (){
        ClientClass client = new ClientClass();
        client.setName(mClienteName.getText().toString());
        client.setPhone(mClientePhone.getText().toString());
        client.setEmail(mClienteMail.getText().toString());
        client.setObs(mClienteHuseType.getSelectedItem().toString() + " " + mClienteLocation.getText().toString());
        return client;
    }
}

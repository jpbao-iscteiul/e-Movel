package com.example.mycrm_teste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class ClientDetailsActivity extends AppCompatActivity {

    private EditText mClienteName;
    private EditText mClienteMail;
    private EditText mClientePhone;
    private EditText mClienteLocation;

    private Spinner mClienteHuseType;

    private Button mUpdateClienteButton;
    private Button mDeleteClienteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);
        Intent intent = getIntent();
        final ClientClass cliente = intent.getParcelableExtra(Contacts.EXTRA_TEXT);

      //  key = getIntent().getStringExtra("clientKey");
        Log.d("Testemunho", "onClick: Client Details recieve  - " + cliente.getName());

        mClienteName = findViewById(R.id.client_name);
        mClienteName.setText(cliente.getName());
        mClienteMail = findViewById(R.id.client_mail);
        mClienteMail.setText(cliente.getEmail());
        mClientePhone = findViewById(R.id.client_phone);
        mClientePhone.setText(cliente.getPhone());
        mClienteLocation = findViewById(R.id.client_location);
        mClienteHuseType = findViewById(R.id.client_housetype);
        mUpdateClienteButton = findViewById(R.id.updateClientButton);
        mDeleteClienteButton = findViewById(R.id.deleteClientButton);

        mUpdateClienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientClass newClient = new ClientClass();
                newClient.setName(mClienteName.getText().toString());
                newClient.setPhone(mClientePhone.getText().toString());
                newClient.setEmail(mClienteMail.getText().toString());
                newClient.setObs(mClienteHuseType.getSelectedItem().toString() + " " + mClienteLocation.getText().toString());
                newClient.setId(cliente.getId());

                new FbDatabaseHelper().updateClient(newClient.getId(), newClient, new FbDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<ClientClass> clients, List<String> keys) {
                    }
                    @Override
                    public void DataIsInserted() {
                    }
                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(ClientDetailsActivity.this, "Updated!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    @Override
                    public void DataIsDeleted() {
                    }
                });
            }
        });

        mDeleteClienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FbDatabaseHelper().deleteClient(cliente.getId(), new FbDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<ClientClass> clients, List<String> keys) {
                    }
                    @Override
                    public void DataIsInserted() {
                    }
                    @Override
                    public void DataIsUpdated() {
                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(ClientDetailsActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });







    }


}

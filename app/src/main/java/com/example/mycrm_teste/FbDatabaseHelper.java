package com.example.mycrm_teste;

import android.provider.ContactsContract;
import android.util.Log;
import android.util.LogPrinter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FbDatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private ArrayList <ClientClass> listaClientes = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<ClientClass> clients, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FbDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("User/User0/ListOfClients");
    }

    public void readClients(final DataStatus dataStatus){
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaClientes.clear();
                ArrayList<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Log.d("Name1", "onBindViewHolder: " + keyNode.getValue().toString());
                    ClientClass client = keyNode.getValue(ClientClass.class);
                    Log.d("Name1", "onDataChange AFTER UPDATE: " + client.getPhone() + " " + client.getName() + " " + client.getObs() + " " + client.getEmail());
                    listaClientes.add(client);
                }
                dataStatus.DataIsLoaded(listaClientes, keys);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addClient(ClientClass client, final DataStatus dataStatus){
        String key = mReference.push().getKey();
        mReference.child(key).setValue(client).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();
            }
        });
    }

    public void updateClient(String key, ClientClass client, final DataStatus dataStatus){
        mReference.child(key).setValue(client).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }

    public void deleteClient (String key, final DataStatus dataStatus){
        mReference.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsDeleted();
            }
        });
    }





}

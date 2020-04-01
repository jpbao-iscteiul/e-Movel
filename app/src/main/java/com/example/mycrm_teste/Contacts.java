package com.example.mycrm_teste;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;

public class Contacts extends AppCompatActivity {

    public static final String EXTRA_TEXT = "TAG_CONTACT";

    public static final int REQUEST_CALL = 1;

    private RecyclerView mRecyclerView;
    private CardsListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button buttonInsert;
    private ImageView phone;


    private List<TemplateContactCard> listOfClients;
    private List<String> clientKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mRecyclerView = findViewById(R.id.recyclerView);
        createContactList();
        setButtons();
    }

    private void removeContact(int position) {
        /*if(position < contactList.size() && position > 0) {
            contactList.remove(position - 1);
            mAdapter.notifyItemRemoved(position - 1);
        }else {
            Toast.makeText(Contacts.this , "Select a valid Contact", Toast.LENGTH_SHORT).show();
        } */
    }

    private void buildRecyclerView(List<TemplateContactCard> contactList, List<String> keys) {
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CardsListAdapter(contactList, keys);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new CardsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                clickItem(position);
            }

            @Override
            public void onCallClick(int position) {
                makePhoneCall(position);
            }

            @Override
            public void onFavPressedClick(int position) {
                final ClientClass client = listOfClients.get(position).getCliente();
                client.setFav(0);
                new FbDatabaseHelper().updateClient(client.getId(), client, new FbDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<ClientClass> clients, List<String> keys) {

                    }
                    @Override
                    public void DataIsInserted() {

                    }
                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(Contacts.this, "Client Removed to Favorites", Toast.LENGTH_SHORT).show();
                        Log.d("ClientA", "DataIsUpdated: TAG " + client.getFav());
                    }
                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
            @Override
            public void onFavClick(int position) {
                final ClientClass client = listOfClients.get(position).getCliente();
                Log.d("ClientB", "DataIsUpdated: TAG " + client.getFav());
                client.setFav(1);
                new FbDatabaseHelper().updateClient(client.getId(), client, new FbDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<ClientClass> clients, List<String> keys) {

                    }
                    @Override
                    public void DataIsInserted() {

                    }
                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(Contacts.this, "Client Added to Favorites", Toast.LENGTH_SHORT).show();
                        Log.d("ClientA", "DataIsUpdated: TAG " + client.getFav());
                    }
                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });

    }

    public void clickItem(int position) {
        Intent intent = new Intent(Contacts.this, ClientDetailsActivity.class);
        ClientClass client = listOfClients.get(position).getCliente();
        intent.putExtra(EXTRA_TEXT, client);
        startActivity(intent);

    }

    private void createContactList() {

        new FbDatabaseHelper().readClients(new FbDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<ClientClass> clients, List<String> keys) {
                listOfClients = new ArrayList<>();
                clientKeys = keys;
                for (int i = 0; i < clients.size(); i++) {
                    listOfClients.add(i, new TemplateContactCard(R.drawable.ic_person_outline_black_24dp, clients.get(i)));
                }
                buildRecyclerView(listOfClients, clientKeys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    private void setButtons() {
        buttonInsert = findViewById(R.id.buttonInsertNewClient);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
            }
        });

        /*
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(editTextRemove.getText().toString());
                removeContact(position);
            }
        });
        */


    }
    private void addContact() {
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Contacts.this, AddClient.class));
            }
        });
    }

    private void makePhoneCall(int position) {
        String phoneNumner = listOfClients.get(position).getCliente().getPhone().trim();
        if (phoneNumner.length() > 0) {

            if (ContextCompat.checkSelfPermission(Contacts.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(Contacts.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

            } else {
                String dial = "tel:" + phoneNumner;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(Contacts.this, "User Does Not Have a Valid Phone Number", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("Ch", "onCreateOptionsMenu: CHEGUEI");
        getMenuInflater().inflate(R.menu.contact_filter, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });



        return true;
    }
}

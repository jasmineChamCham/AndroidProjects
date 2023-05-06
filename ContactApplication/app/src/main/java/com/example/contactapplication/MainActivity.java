package com.example.contactapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.contactapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Contact> contactList = new ArrayList<>();
    private List<Contact> allContacts;
    private ContactAdapter contactAdapter;
    private AppDatabase appDatabase;
    private ContactDao contactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appDatabase = AppDatabase.getInstance(this);
        contactDao = appDatabase.contactDao();
        getContactLists();

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        binding.rvContact.setLayoutManager(new LinearLayoutManager(this));
        if  (contactList != null){
            initRecyclerView();
        }
        else {
            Log.d("Contactlist is null", "Contactlist is null");
        }

        binding.butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewContactActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        contactAdapter.setOnItemLongClickListener(new ContactAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Contact contact) {
                deleteContact(contact);
            }
        });

        contactAdapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contact contact) {
                Intent intent = new Intent(MainActivity.this, NewContactActivity.class);
                intent.putExtra("firstName", contact.getFirstName());
                intent.putExtra("lastName", contact.getLastName());
                intent.putExtra("phone", contact.getPhone());
                intent.putExtra("email", contact.getEmail());
                intent.putExtra("home", contact.getHome());
                intent.putExtra("avatar", contact.getAvatar());
                intent.putExtra("index", contactList.indexOf(contact));
                startActivityForResult(intent, 2);
            }
        });
    }

    private void getContactLists() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                allContacts = contactDao.getAllContacts();
                if (allContacts != null){
                    contactList = new ArrayList<>(allContacts);
                }
                else contactList = new ArrayList<>();
            }
        });
    }

    private void initRecyclerView() {
        binding.rvContact.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter = new ContactAdapter(contactList);
        binding.rvContact.setAdapter(contactAdapter);
    }

    private void addContact(Contact contact){
        contactList.add(contact);
        contactAdapter.notifyDataSetChanged();
        AsyncTask.execute(() -> {
            contactDao.insert(contact);
        });
    }

    private void deleteContact(Contact contact){
        AsyncTask.execute(() -> {
            contactDao.delete(contact);
        });
        runOnUiThread(() -> {
            contactList.remove(contact);
            contactAdapter.notifyDataSetChanged();
        });
    }

    private void updateContact(Contact contact, int index){
        AsyncTask.execute(() -> {
            contactDao.update(contact);
        });
        runOnUiThread(() -> {
            contactList.set(index, contact);
            contactAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_baseline_density_medium_24);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactList.clear();
                if (newText.isEmpty()) {
                    contactList.addAll(allContacts);
                } else {
                    for (Contact contact : allContacts) {
                        if (contact.getFullName().toLowerCase().contains(newText.toLowerCase()))
                            contactList.add(contact);
                    }
                }
                contactAdapter.notifyDataSetChanged();
                return true;
            }
        });

        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String firstName = data.getStringExtra("firstName");
                String lastName = data.getStringExtra("lastName");
                String phone = data.getStringExtra("phone");
                String email = data.getStringExtra("email");
                String home = data.getStringExtra("home");
                String avatar = data.getStringExtra("avatar");
                Log.d("ava in main activity: ", avatar);
                addContact(new Contact(firstName, lastName, phone, email, home, avatar));
            }
        }
        if (requestCode == 2){
            if(resultCode == RESULT_OK) {
                String firstName = data.getStringExtra("firstName");
                String lastName = data.getStringExtra("lastName");
                String phone = data.getStringExtra("phone");
                String email = data.getStringExtra("email");
                String home = data.getStringExtra("home");
                String avatar = data.getStringExtra("avatar");
                int index = data.getIntExtra("index", 0);
                updateContact(new Contact(firstName, lastName, phone, email, home, avatar) , index);
            }
        }
    }


}
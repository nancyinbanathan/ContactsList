package com.example.contactslist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_CONTACT = 1;
    public static final int EDIT_CONTACT = 2;

    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddContact = findViewById(R.id.button_add_contact);
        buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddEditContactActivity.class);
                startActivityForResult(intent,ADD_CONTACT);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ContactAdapter adapter = new ContactAdapter();
        recyclerView.setAdapter(adapter);

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable List<Contact> contacts) {
                adapter.setContacts(contacts);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                contactViewModel.delete(adapter.getContactAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contact contact) {
                Intent intent = new Intent(MainActivity.this, AddEditContactActivity.class );
                intent.putExtra(AddEditContactActivity.EXTRA_ID,contact.getContactId());
                intent.putExtra(AddEditContactActivity.EXTRA_NAME,contact.getContactName());
                intent.putExtra(AddEditContactActivity.EXTRA_PHONE,contact.getContactPhone());
                intent.putExtra(AddEditContactActivity.EXTRA_EMAIL,contact.getContactEmail());
                startActivityForResult(intent,EDIT_CONTACT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CONTACT && resultCode == RESULT_OK){
            String name = data.getStringExtra(AddEditContactActivity.EXTRA_NAME);
            String phone = data.getStringExtra(AddEditContactActivity.EXTRA_PHONE);
            String email = data.getStringExtra(AddEditContactActivity.EXTRA_EMAIL);

            Contact contact = new Contact(name,phone,email);
            contactViewModel.insert(contact);
            Toast.makeText(this, "Contact Saved", Toast.LENGTH_SHORT).show();
        }else if (requestCode == EDIT_CONTACT && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditContactActivity.EXTRA_ID, -1);
            if (id == 1) {
                Toast.makeText(this, "Contact cannot be updated", Toast.LENGTH_SHORT).show();
                return;
            } else {
                String name = data.getStringExtra(AddEditContactActivity.EXTRA_NAME);
                String phone = data.getStringExtra(AddEditContactActivity.EXTRA_PHONE);
                String email = data.getStringExtra(AddEditContactActivity.EXTRA_EMAIL);
                Contact contact = new Contact(name, phone, email);
                contact.setContactId(id);
                contactViewModel.update(contact);
                Toast.makeText(this, "Contact Updated", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Contact not saved", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.delete_all_contacts:
                contactViewModel.deleteAllContacts();
                Toast.makeText(this, "All Contacts deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

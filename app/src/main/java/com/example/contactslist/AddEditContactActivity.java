package com.example.contactslist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditContactActivity extends AppCompatActivity {


    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_NAME = "NAME";
    public static final String EXTRA_PHONE = "PHONE";
    public static final String EXTRA_EMAIL = "EMAIL";

    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        editTextName = findViewById(R.id.editText_name);
        editTextPhone = findViewById(R.id.editText_phone);
        editTextEmail = findViewById(R.id.editText_email);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Contact");
            editTextName.setText(intent.getStringExtra("EXTRA_NAME"));
            editTextPhone.setText(intent.getStringExtra("EXTRA_PHONE"));
            editTextEmail.setText(intent.getStringExtra("EXTRA_EMAIL"));
        }else{
            setTitle("Add Contact");
        }
    }

    private void saveContact(){
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String email = editTextEmail.getText().toString();

        if (name.trim().isEmpty() || phone.trim().isEmpty() || email.trim().isEmpty()){
            Toast.makeText(this, "Please enter valid Name, Phone number and Email"
                    , Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_NAME,name);
        data.putExtra(EXTRA_PHONE,phone);
        data.putExtra(EXTRA_EMAIL,email);
        int id =getIntent().getIntExtra(EXTRA_ID,-1);

        if(id != -1){
            data.putExtra(EXTRA_ID,id);
        }
        setResult(RESULT_OK,data);
        finish();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.contact_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_contact:
                saveContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}

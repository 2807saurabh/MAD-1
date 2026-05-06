package com.example.emergencyalert;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button addContact, viewContacts, sos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addContact = findViewById(R.id.addBtn);
        viewContacts = findViewById(R.id.viewBtn);
        sos = findViewById(R.id.sosBtn);

        addContact.setOnClickListener(v ->
                startActivity(new Intent(this, AddContactActivity.class)));

        viewContacts.setOnClickListener(v ->
                startActivity(new Intent(this, ContactsActivity.class)));

        sos.setOnClickListener(v ->
                startActivity(new Intent(this, SOSActivity.class)));
    }
}

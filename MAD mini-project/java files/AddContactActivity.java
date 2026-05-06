package com.example.emergencyalert;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {

    EditText name, phone;
    Button save;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        save = findViewById(R.id.saveBtn);

        db = new DatabaseHelper(this);

        save.setOnClickListener(v -> {
            db.addContact(name.getText().toString(), phone.getText().toString());
            Toast.makeText(this, "Contact Saved", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
0

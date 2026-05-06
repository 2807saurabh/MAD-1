package com.example.emergencyalert;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password;
    Button register;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        register = findViewById(R.id.registerBtn);

        db = new DatabaseHelper(this);

        register.setOnClickListener(v -> {
            db.insertUser(username.getText().toString(), password.getText().toString());
            Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
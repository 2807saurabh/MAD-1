package com.example.expliciteintent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityTwo extends AppCompatActivity {

    Button button11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        button11 = findViewById(R.id.button11);

        button11.setOnClickListener(v -> {
            Intent i = new Intent(ActivityTwo.this, MainActivity.class);
            startActivity(i);
        });
    }
}
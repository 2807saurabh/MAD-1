package com.example.layouts;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnFrame).setOnClickListener(v -> startActivity(new Intent(this, FrameActivity.class)));
        findViewById(R.id.btnGrid).setOnClickListener(v -> startActivity(new Intent(this, GridActivity.class)));
        findViewById(R.id.btnHorizontal).setOnClickListener(v -> startActivity(new Intent(this, HorizontalActivity.class)));
        findViewById(R.id.btnList).setOnClickListener(v -> startActivity(new Intent(this, ListActivity.class)));
        findViewById(R.id.btnRelative).setOnClickListener(v -> startActivity(new Intent(this, RelativeActivity.class)));
        findViewById(R.id.btnTable).setOnClickListener(v -> startActivity(new Intent(this, TableActivity.class)));
        findViewById(R.id.btnVerticle).setOnClickListener(v -> startActivity(new Intent(this, VerticleActivity.class)));

    }
}
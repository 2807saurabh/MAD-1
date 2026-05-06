package com.example.inputcontrols;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnToggle).setOnClickListener(v ->
                startActivity(new Intent(this, ToggleActivity.class)));

        findViewById(R.id.btnRadio).setOnClickListener(v ->
                startActivity(new Intent(this, RadioSpinnerActivity.class)));

        findViewById(R.id.btnProgress).setOnClickListener(v ->
                startActivity(new Intent(this, ProgressActivity.class)));

        findViewById(R.id.btnRating).setOnClickListener(v ->
                startActivity(new Intent(this, RatingAlertActivity.class)));
    }
}
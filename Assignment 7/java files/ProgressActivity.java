package com.example.inputcontrols;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

public class ProgressActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        ProgressBar determinateBar = findViewById(R.id.detProgress);

        // Simulating a task that takes time
        new Handler().postDelayed(() -> determinateBar.setProgress(50), 2000);
        new Handler().postDelayed(() -> determinateBar.setProgress(100), 4000);
    }
}
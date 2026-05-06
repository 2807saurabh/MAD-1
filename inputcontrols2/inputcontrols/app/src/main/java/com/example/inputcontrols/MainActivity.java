package com.example.inputcontrols;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButton;
    RatingBar ratingBar;
    ProgressBar progressBar;
    Button buttonProgress, buttonClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageButton = findViewById(R.id.imageButton);
        ratingBar = findViewById(R.id.ratingBar);
        progressBar = findViewById(R.id.progress);
        buttonProgress = findViewById(R.id.buttonProgress);
        buttonClose = findViewById(R.id.buttonClose);

        imageButton.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "Image Button Clicked", Toast.LENGTH_SHORT).show()
        );

        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) ->
                Toast.makeText(MainActivity.this, "Rating: " + rating, Toast.LENGTH_SHORT).show()
        );

        buttonProgress.setOnClickListener(v ->
                progressBar.setVisibility(View.VISIBLE)
        );

        buttonClose.setOnClickListener(v ->
                progressBar.setVisibility(View.INVISIBLE)
        );

        Toast.makeText(MainActivity.this, "Welcome to the App!", Toast.LENGTH_SHORT).show();
        getOnBackPressedDispatcher().addCallback(this, new androidx.activity.OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                        .setTitle("Exit App")
                        .setMessage("Do you want to exit?")
                        .setPositiveButton("Yes", (dialog, which) -> finish())
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });
    }

}
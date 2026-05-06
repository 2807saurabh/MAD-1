package com.example.inputcontrols;


import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RatingAlertActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_alert);

        RatingBar ratingBar = findViewById(R.id.myRating);
        ratingBar.setOnRatingBarChangeListener((rBar, rating, fromUser) ->
                Toast.makeText(this, "Rating: " + rating, Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnShowAlert).setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Alert Popup")
                    .setMessage("This is a custom alert!")
                    .setPositiveButton("OK", null)
                    .show();
        });
    }
}
package com.example.intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. WhatsApp to Raj Nikam
        Button btnWhatsapp = findViewById(R.id.btnWhatsapp);
        btnWhatsapp.setOnClickListener(v -> {
            String phoneNumber = "918010438938"; // Removed the '+' sign for URL compatibility
            String message = "Hi Raj Nikam";
            String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        // 2. Open Instagram
        Button btnInsta = findViewById(R.id.btnInsta);
        btnInsta.setOnClickListener(v -> {
            Intent i = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
            if (i != null) {
                startActivity(i);
            } else {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com")));
            }
        });

        // 3. Open Alarm
        Button btnAlarm = findViewById(R.id.btnAlarm);
        btnAlarm.setOnClickListener(v -> {
            Intent i = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
            startActivity(i);
        });

        // 4. Open Camera
        Button btnCamera = findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(v -> {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            }
        });
    }
}
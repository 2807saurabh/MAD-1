package com.example.emergencyalert;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class SOSActivity extends AppCompatActivity {

    Button sosBtn;
    TextView statusText;
    DatabaseHelper db;

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        sosBtn = findViewById(R.id.sosBtn);
        statusText = findViewById(R.id.statusText);
        db = new DatabaseHelper(this);

        sosBtn.setOnClickListener(v -> checkPermissionsAndSendSOS());
    }

    private void checkPermissionsAndSendSOS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    PERMISSION_REQUEST_CODE
            );
        } else {
            sendSOS();
        }
    }

    private void sendSOS() {
        Cursor c = db.getContacts();

        if (c == null || c.getCount() == 0) {
            Toast.makeText(this, "No contacts found!", Toast.LENGTH_SHORT).show();
            statusText.setText("No contacts found!");
            return;
        }

        String message = buildEmergencyMessage();

        SmsManager smsManager = SmsManager.getDefault();
        String firstNumber = null;
        boolean smsSent = false;

        while (c.moveToNext()) {
            String phone = c.getString(2);

            if (phone != null) {
                phone = phone.replaceAll("\\s+", "").trim();

                if (!phone.isEmpty()) {
                    ArrayList<String> parts = smsManager.divideMessage(message);
                    smsManager.sendMultipartTextMessage(phone, null, parts, null, null);

                    smsSent = true;

                    if (firstNumber == null) {
                        firstNumber = phone;
                    }
                }
            }
        }

        c.close();

        if (smsSent) {
            statusText.setText("✅ SOS Sent Successfully!\n\n" + message);
            Toast.makeText(this, "SOS sent to saved contacts!", Toast.LENGTH_LONG).show();
        } else {
            statusText.setText("No valid contact numbers found.");
            Toast.makeText(this, "No valid contact numbers found!", Toast.LENGTH_LONG).show();
            return;
        }

        if (firstNumber != null && !firstNumber.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + firstNumber));
            startActivity(intent);
        }
    }

    private String buildEmergencyMessage() {
        String message = "EMERGENCY ALERT!\nI am in danger. Please help me.";

        try {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (locationManager == null) {
                return message + "\nLocation not available.";
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return message + "\nLocation permission not granted.";
            }

            Location location = null;

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

            if (location == null && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (location != null) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                message = message + "\nMy Location:\nhttps://maps.google.com/?q=" + lat + "," + lng;
            } else {
                message = message + "\nLocation not available.";
            }

        } catch (Exception e) {
            message = message + "\nLocation error.";
        }

        return message;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            checkPermissionsAndSendSOS();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allGranted = true;

            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }

            if (allGranted) {
                sendSOS();
            } else {
                Toast.makeText(this, "Please allow SMS and Location permissions", Toast.LENGTH_LONG).show();
            }
        }
    }
}
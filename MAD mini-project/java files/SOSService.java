package com.example.emergencyalert;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.database.Cursor;

public class SOSService extends Service {

    DatabaseHelper db;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        db = new DatabaseHelper(this);
        Cursor c = db.getContacts();

        String msg = "🚨 EMERGENCY! I need help!";

        SmsManager sms = SmsManager.getDefault();

        while (c.moveToNext()) {
            String phone = c.getString(2);
            sms.sendTextMessage(phone, null, msg, null, null);
        }

        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
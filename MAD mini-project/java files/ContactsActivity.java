package com.example.emergencyalert;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    ListView listView;
    DatabaseHelper db;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        listView = findViewById(R.id.listView);
        db = new DatabaseHelper(this);

        loadContacts();
    }

    private void loadContacts() {
        Cursor c = db.getContacts();
        list = new ArrayList<>();

        while (c.moveToNext()) {
            list.add(c.getString(1) + " - " + c.getString(2));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list);

        listView.setAdapter(adapter);


        listView.setOnItemLongClickListener((parent, view, position, id) -> {

            String item = list.get(position);
            String phone = item.split(" - ")[1];

            new AlertDialog.Builder(this)
                    .setTitle("Delete Contact")
                    .setMessage("Are you sure you want to delete?")
                    .setPositiveButton("YES", (d, w) -> {

                        boolean deleted = db.deleteContact(phone);

                        if (deleted) {
                            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                            loadContacts(); // refresh list
                        }
                    })
                    .setNegativeButton("NO", null)
                    .show();

            return true;
        });
    }
}
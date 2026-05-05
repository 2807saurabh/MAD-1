package com.example.inputcontrols;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;

public class ToggleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle);

        ToggleButton toggle = findViewById(R.id.myToggle);
        CheckBox check = findViewById(R.id.myCheckBox);

        toggle.setOnCheckedChangeListener((buttonView, isChecked) ->
                Toast.makeText(this, "Toggle: " + isChecked, Toast.LENGTH_SHORT).show());

        check.setOnCheckedChangeListener((buttonView, isChecked) ->
                Toast.makeText(this, "Checked: " + isChecked, Toast.LENGTH_SHORT).show());
    }
}
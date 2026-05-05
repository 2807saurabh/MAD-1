package com.example.inputcontrols;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RadioSpinnerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_spinner);

        RadioGroup rg = findViewById(R.id.myRadioGroup);
        rg.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = findViewById(checkedId);
            Toast.makeText(this, "Selected: " + rb.getText(), Toast.LENGTH_SHORT).show();
        });

        Spinner spinner = findViewById(R.id.mySpinner);
        String[] options = {"Option 1", "Option 2", "Option 3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        spinner.setAdapter(adapter);
    }
}
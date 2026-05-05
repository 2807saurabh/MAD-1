package com.example.internalstorageapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    // Step 1: Define Constants and Variables
    private static final String FILE_NAME = "lab_data.txt"; // Name of the file in internal storage
    
    private EditText editTextData;
    private TextView textViewDisplay;
    private Button buttonSave, buttonRead, buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Enable edge-to-edge for a modern look
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        // Apply window insets to handle system bars padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Step 2: Initialize UI Components
        editTextData = findViewById(R.id.editTextData);
        textViewDisplay = findViewById(R.id.textViewDisplay);
        buttonSave = findViewById(R.id.buttonSave);
        buttonRead = findViewById(R.id.buttonRead);
        buttonClear = findViewById(R.id.buttonClear);

        // Step 3: Set Click Listeners
        buttonSave.setOnClickListener(v -> saveData());
        buttonRead.setOnClickListener(v -> readData());
        buttonClear.setOnClickListener(v -> clearData());
    }

    /**
     * Writes text from the EditText to a private file in internal storage.
     */
    private void saveData() {
        String data = editTextData.getText().toString().trim();

        if (data.isEmpty()) {
            Toast.makeText(this, "Please enter some data to save", Toast.LENGTH_SHORT).show();
            return;
        }

        // Try-with-resources ensures the stream is closed automatically
        try (FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            // Write string data as bytes
            fos.write(data.getBytes(StandardCharsets.UTF_8));
            
            // Provide feedback to the user
            Toast.makeText(this, "Data saved successfully to " + FILE_NAME, Toast.LENGTH_SHORT).show();
            editTextData.setText(""); // Clear input after saving
        } catch (Exception e) {
            // Handle potential IO exceptions
            e.printStackTrace();
            Toast.makeText(this, "Error saving data: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Reads text from the private file in internal storage and displays it.
     */
    private void readData() {
        StringBuilder stringBuilder = new StringBuilder();

        // Try-with-resources to manage stream lifetime
        try (FileInputStream fis = openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            // Display read content in the TextView
            String content = stringBuilder.toString().trim();
            if (content.isEmpty()) {
                textViewDisplay.setText("[File is empty]");
            } else {
                textViewDisplay.setText(content);
            }
            
            Toast.makeText(this, "Data read from storage", Toast.LENGTH_SHORT).show();

        } catch (java.io.FileNotFoundException e) {
            // Specifically handle case where file doesn't exist yet
            textViewDisplay.setText("[No data saved yet]");
            Toast.makeText(this, "No saved data found", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // Handle other IO exceptions
            e.printStackTrace();
            Toast.makeText(this, "Error reading data: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Deletes the local file from internal storage.
     */
    private void clearData() {
        // deleteFile() returns true if file was successfully deleted
        boolean deleted = deleteFile(FILE_NAME);
        
        if (deleted) {
            textViewDisplay.setText("[Data cleared]");
            editTextData.setText("");
            Toast.makeText(this, "File deleted from storage", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nothing to delete or error occurred", Toast.LENGTH_SHORT).show();
        }
    }
}
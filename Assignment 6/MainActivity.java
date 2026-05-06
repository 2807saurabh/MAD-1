package com.example.arithmaticoperation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText n1, n2;
    private TextView res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link Java variables to XML IDs
        n1 = findViewById(R.id.num1);
        n2 = findViewById(R.id.num2);
        res = findViewById(R.id.result);

        // Click Listeners
        findViewById(R.id.btn_add).setOnClickListener(v -> calculate('+'));
        findViewById(R.id.btn_sub).setOnClickListener(v -> calculate('-'));
        findViewById(R.id.btn_mul).setOnClickListener(v -> calculate('*'));
        findViewById(R.id.btn_div).setOnClickListener(v -> calculate('/'));
    }

    private void calculate(char op) {
        String s1 = n1.getText().toString();
        String s2 = n2.getText().toString();

        if (s1.isEmpty() || s2.isEmpty()) {
            Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        double v1 = Double.parseDouble(s1);
        double v2 = Double.parseDouble(s2);
        double output = 0;

        switch (op) {
            case '+': output = v1 + v2; break;
            case '-': output = v1 - v2; break;
            case '*': output = v1 * v2; break;
            case '/':
                if (v2 != 0) {
                    output = v1 / v2;
                } else {
                    res.setText("Cannot divide by 0");
                    return;
                }
                break;
        }

        DecimalFormat df = new DecimalFormat("###.##");
        res.setText("Result: " + df.format(output));
    }
}
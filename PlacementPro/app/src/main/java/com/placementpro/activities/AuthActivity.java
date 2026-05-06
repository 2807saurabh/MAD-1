package com.placementpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.placementpro.R;
import com.placementpro.utils.SessionManager;

public class AuthActivity extends AppCompatActivity {

    private LinearLayout loginLayout, registerLayout;
    private EditText etLoginEmail, etLoginPass;
    private EditText etRegName, etRegEmail, etRegPass, etRegCollege, etRegTarget;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        session = new SessionManager(this);

        loginLayout = findViewById(R.id.layout_login);
        registerLayout = findViewById(R.id.layout_register);

        etLoginEmail = findViewById(R.id.et_login_email);
        etLoginPass = findViewById(R.id.et_login_pass);
        etRegName = findViewById(R.id.et_reg_name);
        etRegEmail = findViewById(R.id.et_reg_email);
        etRegPass = findViewById(R.id.et_reg_pass);
        etRegCollege = findViewById(R.id.et_reg_college);
        etRegTarget = findViewById(R.id.et_reg_target);

        findViewById(R.id.btn_login).setOnClickListener(v -> handleLogin());
        findViewById(R.id.btn_register).setOnClickListener(v -> handleRegister());
        findViewById(R.id.tv_switch_to_register).setOnClickListener(v -> showRegister());
        findViewById(R.id.tv_switch_to_login).setOnClickListener(v -> showLogin());
    }

    private void handleLogin() {
        String email = etLoginEmail.getText().toString().trim();
        String pass = etLoginPass.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check saved credentials
        String savedEmail = session.getEmail();
        if (!savedEmail.isEmpty() && savedEmail.equals(email)) {
            openDashboard();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Invalid Credentials")
                    .setMessage("No account found. Please register first.")
                    .setPositiveButton("Register", (d, w) -> showRegister())
                    .setNegativeButton("Cancel", null).show();
        }
    }

    private void handleRegister() {
        String name = etRegName.getText().toString().trim();
        String email = etRegEmail.getText().toString().trim();
        String pass = etRegPass.getText().toString();
        String college = etRegCollege.getText().toString().trim();
        String target = etRegTarget.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = "user_" + System.currentTimeMillis();
        session.createSession(userId, name, email,
                college.isEmpty() ? "B.Tech CSE" : college,
                target.isEmpty() ? "Google, Amazon, Microsoft" : target);

        new AlertDialog.Builder(this)
                .setTitle("🎉 Welcome, " + name + "!")
                .setMessage("Your account is ready. Let's crack placements!")
                .setPositiveButton("Start Journey", (d, w) -> openDashboard())
                .setCancelable(false).show();
    }

    private void openDashboard() {
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }

    private void showRegister() {
        loginLayout.setVisibility(View.GONE);
        registerLayout.setVisibility(View.VISIBLE);
    }

    private void showLogin() {
        registerLayout.setVisibility(View.GONE);
        loginLayout.setVisibility(View.VISIBLE);
    }
}

package com.placementpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.placementpro.R;
import com.placementpro.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView tvTitle = findViewById(R.id.tv_splash_title);
        TextView tvSub = findViewById(R.id.tv_splash_sub);

        AlphaAnimation anim = new AlphaAnimation(0f, 1f);
        anim.setDuration(1200);
        tvTitle.startAnimation(anim);
        tvSub.startAnimation(anim);

        SessionManager session = new SessionManager(this);
        new Handler().postDelayed(() -> {
            Intent intent = session.isLoggedIn()
                    ? new Intent(this, DashboardActivity.class)
                    : new Intent(this, AuthActivity.class);
            startActivity(intent);
            finish();
        }, 2500);
    }
}

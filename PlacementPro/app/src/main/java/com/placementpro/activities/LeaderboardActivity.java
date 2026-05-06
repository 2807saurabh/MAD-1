package com.placementpro.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.placementpro.R;
import com.placementpro.utils.SessionManager;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        SessionManager session = new SessionManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar_leaderboard);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Leaderboard");
        }

        // Show user's own rank with mock data
        TextView tvYourRank = findViewById(R.id.tv_your_rank);
        TextView tvYourXP = findViewById(R.id.tv_your_xp);
        TextView tvYourName = findViewById(R.id.tv_your_name);

        tvYourName.setText(session.getName());
        tvYourXP.setText(session.getXP() + " XP");

        int xp = session.getXP();
        String rank = xp > 500 ? "#1 🏆" : xp > 300 ? "#3 🥉" : xp > 150 ? "#7 ⭐" : "#12";
        tvYourRank.setText(rank);

        // Mock leaderboard
        LinearLayout leaderboardContainer = findViewById(R.id.leaderboard_container);
        String[][] mockUsers = {
                {"🥇", "Arjun Sharma", "1250 XP", "#FF6B6B"},
                {"🥈", "Priya Mehta", "1100 XP", "#4ECDC4"},
                {"🥉", "Rohit Kumar", "980 XP", "#45B7D1"},
                {"4️⃣", "Sneha Patel", "870 XP", "#96CEB4"},
                {"5️⃣", "Aditya Singh", "760 XP", "#FFEAA7"},
                {"6️⃣", "Kavya Reddy", "650 XP", "#DDA0DD"},
                {"7️⃣", "Rahul Gupta", "540 XP", "#98FB98"},
                {"8️⃣", session.getName(), xp + " XP", "#FFD700"},
        };

        for (String[] user : mockUsers) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setPadding(32, 24, 32, 24);

            TextView tvRank = new TextView(this);
            tvRank.setText(user[0]);
            tvRank.setTextSize(20);
            tvRank.setMinWidth(80);

            TextView tvName = new TextView(this);
            tvName.setText(user[1]);
            tvName.setTextSize(16);
            tvName.setTextColor(getResources().getColor(R.color.text_primary));
            tvName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

            TextView tvXp = new TextView(this);
            tvXp.setText(user[2]);
            tvXp.setTextSize(14);
            tvXp.setTextColor(getResources().getColor(R.color.accent_color));

            row.addView(tvRank);
            row.addView(tvName);
            row.addView(tvXp);
            leaderboardContainer.addView(row);

            View divider = new View(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 1);
            divider.setLayoutParams(lp);
            divider.setBackgroundColor(getResources().getColor(R.color.divider_color));
            leaderboardContainer.addView(divider);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { onBackPressed(); return true; }
        return super.onOptionsItemSelected(item);
    }
}

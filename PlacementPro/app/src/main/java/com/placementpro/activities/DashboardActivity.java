package com.placementpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import com.placementpro.R;
import com.placementpro.database.DatabaseHelper;
import com.placementpro.utils.SessionManager;
import com.placementpro.utils.SubjectDataHelper;
import com.placementpro.models.Subject;
import com.placementpro.models.Goal;

public class DashboardActivity extends AppCompatActivity {

    private SessionManager session;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        session = new SessionManager(this);
        db = DatabaseHelper.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("");

        setupUI();
        setupCards();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupUI(); // refresh stats
    }

    private void setupUI() {
        String userId = session.getUserId();

        TextView tvName = findViewById(R.id.tv_user_name);
        TextView tvCollege = findViewById(R.id.tv_college);
        TextView tvTarget = findViewById(R.id.tv_target);
        TextView tvStreak = findViewById(R.id.tv_streak);
        TextView tvXP = findViewById(R.id.tv_xp);
        TextView tvTasksCount = findViewById(R.id.tv_tasks_count);
        TextView tvTopicsCount = findViewById(R.id.tv_topics_count);
        TextView tvGoalsCount = findViewById(R.id.tv_goals_count);

        tvName.setText("Hey, " + session.getName() + "! 👋");
        tvCollege.setText(session.getCollege());
        tvTarget.setText("🎯 " + session.getTargetCompany());
        tvStreak.setText(session.getStreak() + " day streak 🔥");
        tvXP.setText(session.getXP() + " XP");

        int pending = db.getPendingTaskCount(userId);
        int completed = db.getCompletedTaskCount(userId);
        tvTasksCount.setText(pending + " pending");

        int topics = db.getTotalCompletedTopics(userId);
        int totalTopics = 0;
        for (Subject s : SubjectDataHelper.getAllSubjects()) totalTopics += s.getTotalTopics();
        tvTopicsCount.setText(topics + "/" + totalTopics + " done");

        int goalsDone = 0;
        for (Goal g : db.getGoals(userId)) if (g.isDone()) goalsDone++;
        tvGoalsCount.setText(goalsDone + " completed");
    }

    private void setupCards() {
        // Grid cards navigation
        findViewById(R.id.card_subjects).setOnClickListener(v ->
                startActivity(new Intent(this, SubjectsActivity.class)));
        findViewById(R.id.card_tasks).setOnClickListener(v ->
                startActivity(new Intent(this, TaskActivity.class)));
        findViewById(R.id.card_progress).setOnClickListener(v ->
                startActivity(new Intent(this, ProgressActivity.class)));
        findViewById(R.id.card_goals).setOnClickListener(v ->
                startActivity(new Intent(this, GoalActivity.class)));
        findViewById(R.id.card_schedule).setOnClickListener(v ->
                startActivity(new Intent(this, ScheduleActivity.class)));
        findViewById(R.id.card_quiz).setOnClickListener(v ->
                startActivity(new Intent(this, QuizActivity.class)));
        findViewById(R.id.card_leaderboard).setOnClickListener(v ->
                startActivity(new Intent(this, LeaderboardActivity.class)));
        findViewById(R.id.card_resources).setOnClickListener(v ->
                showResourcesDialog());
    }

    private void showResourcesDialog() {
        String[] resources = {
                "📖 LeetCode — Daily DSA practice",
                "🎓 GeeksforGeeks — CS fundamentals",
                "📺 YouTube — Abdul Bari (Algorithms)",
                "📚 Striver's A2Z DSA Sheet",
                "🔗 GitHub — Open source contributions",
                "📰 Medium — Tech blogs & articles",
                "🎯 InterviewBit — Interview prep",
                "💼 LinkedIn — Network building"
        };
        new AlertDialog.Builder(this)
                .setTitle("📚 Placement Resources")
                .setItems(resources, null)
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_profile) {
            showProfile();
        } else if (id == R.id.action_logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Logout?")
                    .setMessage("See you soon! Keep grinding 💪")
                    .setPositiveButton("Logout", (d, w) -> {
                        session.logout();
                        startActivity(new Intent(this, AuthActivity.class));
                        finish();
                    })
                    .setNegativeButton("Stay", null).show();
        }
        return true;
    }

    private void showProfile() {
        String msg = "👤 Name: " + session.getName() +
                "\n📧 Email: " + session.getEmail() +
                "\n🏫 College: " + session.getCollege() +
                "\n🎯 Target: " + session.getTargetCompany() +
                "\n🔥 Streak: " + session.getStreak() + " days" +
                "\n⭐ XP: " + session.getXP();
        new AlertDialog.Builder(this)
                .setTitle("My Profile")
                .setMessage(msg)
                .setPositiveButton("Close", null).show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit App?")
                .setMessage("Keep grinding! See you tomorrow 💪")
                .setPositiveButton("Exit", (d, w) -> finishAffinity())
                .setNegativeButton("Stay", null).show();
    }
}

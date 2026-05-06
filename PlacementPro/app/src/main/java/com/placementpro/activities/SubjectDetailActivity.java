package com.placementpro.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.placementpro.R;
import com.placementpro.database.DatabaseHelper;
import com.placementpro.models.Subject;
import com.placementpro.utils.SessionManager;
import com.placementpro.utils.SubjectDataHelper;
import java.util.List;

public class SubjectDetailActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private SessionManager session;
    private String subjectId, subjectName;
    private LinearLayout topicsContainer;
    private TextView tvProgress, tvCompleted;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_detail);

        db = DatabaseHelper.getInstance(this);
        session = new SessionManager(this);

        subjectId = getIntent().getStringExtra("subject_id");
        subjectName = getIntent().getStringExtra("subject_name");

        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(subjectName);
        }

        topicsContainer = findViewById(R.id.topics_container);
        tvProgress = findViewById(R.id.tv_progress_percent);
        tvCompleted = findViewById(R.id.tv_completed_count);
        progressBar = findViewById(R.id.progress_bar_subject);

        loadSubjectTopics();
    }

    private void loadSubjectTopics() {
        Subject subject = null;
        for (Subject s : SubjectDataHelper.getAllSubjects()) {
            if (s.getId().equals(subjectId)) { subject = s; break; }
        }
        if (subject == null) return;

        List<String> topics = subject.getTopics();
        topicsContainer.removeAllViews();

        for (String topic : topics) {
            CheckBox cb = new CheckBox(this);
            cb.setText(topic);
            cb.setTextColor(getResources().getColor(R.color.text_primary));
            cb.setTextSize(15);
            cb.setPadding(16, 20, 16, 20);

            boolean done = db.isTopicCompleted(session.getUserId(), subjectId, topic);
            cb.setChecked(done);

            cb.setOnCheckedChangeListener((btn, isChecked) -> {
                db.saveTopicProgress(session.getUserId(), subjectId, topic, isChecked);
                if (isChecked) session.addXP(10);
                updateProgress(topics.size());
            });

            topicsContainer.addView(cb);

            // Divider
            View divider = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.setMargins(16, 0, 16, 0);
            divider.setLayoutParams(params);
            divider.setBackgroundColor(getResources().getColor(R.color.divider_color));
            topicsContainer.addView(divider);
        }

        updateProgress(topics.size());
    }

    private void updateProgress(int total) {
        int completed = db.getCompletedTopicsCount(session.getUserId(), subjectId);
        int percent = total > 0 ? (completed * 100) / total : 0;
        tvProgress.setText(percent + "%");
        tvCompleted.setText(completed + "/" + total + " topics completed");
        progressBar.setProgress(percent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { onBackPressed(); return true; }
        return super.onOptionsItemSelected(item);
    }
}

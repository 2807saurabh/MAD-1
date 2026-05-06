package com.placementpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.placementpro.R;
import com.placementpro.adapters.SubjectAdapter;
import com.placementpro.database.DatabaseHelper;
import com.placementpro.models.Subject;
import com.placementpro.utils.SessionManager;
import com.placementpro.utils.SubjectDataHelper;
import java.util.List;

public class SubjectsActivity extends AppCompatActivity {

    private RecyclerView rvSubjects;
    private SessionManager session;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        session = new SessionManager(this);
        db = DatabaseHelper.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar_subjects);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Subjects");
        }

        rvSubjects = findViewById(R.id.rv_subjects);
        rvSubjects.setLayoutManager(new LinearLayoutManager(this));

        List<Subject> subjects = SubjectDataHelper.getAllSubjects();
        SubjectAdapter adapter = new SubjectAdapter(subjects, session.getUserId(), db, subject -> {
            Intent intent = new Intent(this, SubjectDetailActivity.class);
            intent.putExtra("subject_id", subject.getId());
            intent.putExtra("subject_name", subject.getName());
            startActivity(intent);
        });
        rvSubjects.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { onBackPressed(); return true; }
        return super.onOptionsItemSelected(item);
    }
}

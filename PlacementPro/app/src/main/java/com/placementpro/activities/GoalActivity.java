package com.placementpro.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.placementpro.R;
import com.placementpro.adapters.GoalAdapter;
import com.placementpro.database.DatabaseHelper;
import com.placementpro.models.Goal;
import com.placementpro.utils.SessionManager;
import java.util.List;

public class GoalActivity extends AppCompatActivity {

    private RecyclerView rvGoals;
    private TextView tvEmpty;
    private DatabaseHelper db;
    private SessionManager session;
    private List<Goal> goalList;
    private GoalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        db = DatabaseHelper.getInstance(this);
        session = new SessionManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar_goal);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Goals");
        }

        rvGoals = findViewById(R.id.rv_goals);
        tvEmpty = findViewById(R.id.tv_no_goals);
        rvGoals.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.fab_add_goal).setOnClickListener(v -> showAddGoalDialog());
        loadGoals();
    }

    private void loadGoals() {
        goalList = db.getGoals(session.getUserId());

        if (goalList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvGoals.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvGoals.setVisibility(View.VISIBLE);
        }

        adapter = new GoalAdapter(goalList,
                (goal, progress) -> {
                    db.updateGoalProgress(goal.getId(), progress);
                    loadGoals();
                },
                goal -> {
                    new AlertDialog.Builder(this)
                            .setTitle("Delete Goal?")
                            .setMessage("\"" + goal.getTitle() + "\"")
                            .setPositiveButton("Delete", (d, w) -> {
                                db.deleteGoal(goal.getId());
                                loadGoals();
                            })
                            .setNegativeButton("Cancel", null).show();
                });
        rvGoals.setAdapter(adapter);
    }

    private void showAddGoalDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_add_goal, null);
        EditText etTitle = view.findViewById(R.id.et_goal_title);
        EditText etDesc = view.findViewById(R.id.et_goal_desc);
        EditText etTarget = view.findViewById(R.id.et_goal_target);
        EditText etUnit = view.findViewById(R.id.et_goal_unit);
        EditText etDate = view.findViewById(R.id.et_goal_date);

        new AlertDialog.Builder(this)
                .setTitle("🎯 Set New Goal")
                .setView(view)
                .setPositiveButton("Add Goal", (dialog, which) -> {
                    String title = etTitle.getText().toString().trim();
                    if (TextUtils.isEmpty(title)) {
                        Toast.makeText(this, "Title required", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int target = 100;
                    try { target = Integer.parseInt(etTarget.getText().toString().trim()); }
                    catch (NumberFormatException ignored) {}

                    Goal goal = new Goal(
                            session.getUserId(), title,
                            etDesc.getText().toString().trim(),
                            target,
                            etUnit.getText().toString().trim().isEmpty() ? "%" : etUnit.getText().toString().trim(),
                            etDate.getText().toString().trim()
                    );
                    db.addGoal(goal);
                    session.addXP(15);
                    loadGoals();
                    Toast.makeText(this, "Goal set! +15 XP 🎯", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { onBackPressed(); return true; }
        return super.onOptionsItemSelected(item);
    }
}

package com.placementpro.activities;

import android.app.DatePickerDialog;
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
import com.placementpro.adapters.TaskAdapter;
import com.placementpro.database.DatabaseHelper;
import com.placementpro.models.Task;
import com.placementpro.utils.SessionManager;
import java.util.Calendar;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private RecyclerView rvTasks;
    private TextView tvEmpty, tvPending, tvCompleted;
    private TaskAdapter adapter;
    private List<Task> taskList;
    private DatabaseHelper db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        db = DatabaseHelper.getInstance(this);
        session = new SessionManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar_task);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Tasks");
        }

        rvTasks = findViewById(R.id.rv_tasks);
        tvEmpty = findViewById(R.id.tv_no_tasks);
        tvPending = findViewById(R.id.tv_pending_count);
        tvCompleted = findViewById(R.id.tv_completed_count);

        rvTasks.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.fab_add_task).setOnClickListener(v -> showAddTaskDialog());
        loadTasks();
    }

    private void loadTasks() {
        taskList = db.getTasksByUser(session.getUserId());

        if (taskList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvTasks.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvTasks.setVisibility(View.VISIBLE);
        }

        int pending = 0, done = 0;
        for (Task t : taskList) { if (t.isDone()) done++; else pending++; }
        tvPending.setText(pending + " Pending");
        tvCompleted.setText(done + " Done");

        adapter = new TaskAdapter(taskList,
                task -> {
                    db.toggleTaskDone(task.getId(), !task.isDone());
                    if (!task.isDone()) session.addXP(20);
                    loadTasks();
                },
                task -> {
                    new AlertDialog.Builder(this)
                            .setTitle("Delete Task?")
                            .setMessage("\"" + task.getTitle() + "\"")
                            .setPositiveButton("Delete", (d, w) -> {
                                db.deleteTask(task.getId());
                                loadTasks();
                            })
                            .setNegativeButton("Cancel", null).show();
                });
        rvTasks.setAdapter(adapter);
    }

    private void showAddTaskDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_add_task, null);
        EditText etTitle = view.findViewById(R.id.et_task_title);
        EditText etDesc = view.findViewById(R.id.et_task_desc);
        Spinner spinnerPriority = view.findViewById(R.id.spinner_priority);
        Spinner spinnerCategory = view.findViewById(R.id.spinner_category);
        TextView tvDueDate = view.findViewById(R.id.tv_due_date);

        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"HIGH", "MEDIUM", "LOW"});
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(priorityAdapter);
        spinnerPriority.setSelection(1);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"DSA Practice", "Core CS", "Coding", "Resume", "Mock Interview",
                        "Project", "Reading", "Other"});
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        final String[] selectedDate = {"No due date"};
        tvDueDate.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            new DatePickerDialog(this, (dp, y, m, d) -> {
                selectedDate[0] = d + "/" + (m + 1) + "/" + y;
                tvDueDate.setText(selectedDate[0]);
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
        });

        new AlertDialog.Builder(this)
                .setTitle("➕ Add New Task")
                .setView(view)
                .setPositiveButton("Add Task", (dialog, which) -> {
                    String title = etTitle.getText().toString().trim();
                    if (TextUtils.isEmpty(title)) {
                        Toast.makeText(this, "Title required", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Task task = new Task(
                            session.getUserId(), title,
                            etDesc.getText().toString().trim(),
                            spinnerPriority.getSelectedItem().toString(),
                            selectedDate[0],
                            spinnerCategory.getSelectedItem().toString()
                    );
                    db.addTask(task);
                    loadTasks();
                    Toast.makeText(this, "Task added! +5 XP", Toast.LENGTH_SHORT).show();
                    session.addXP(5);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { onBackPressed(); return true; }
        return super.onOptionsItemSelected(item);
    }
}

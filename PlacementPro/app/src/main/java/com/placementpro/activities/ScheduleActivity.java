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
import com.placementpro.adapters.ScheduleAdapter;
import com.placementpro.database.DatabaseHelper;
import com.placementpro.models.ScheduleItem;
import com.placementpro.utils.SessionManager;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    private RecyclerView rvSchedule;
    private TextView tvEmpty;
    private DatabaseHelper db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        db = DatabaseHelper.getInstance(this);
        session = new SessionManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar_schedule);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Study Schedule");
        }

        rvSchedule = findViewById(R.id.rv_schedule);
        tvEmpty = findViewById(R.id.tv_no_schedule);
        rvSchedule.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.fab_add_schedule).setOnClickListener(v -> showAddScheduleDialog());
        loadSchedule();
    }

    private void loadSchedule() {
        List<ScheduleItem> items = db.getSchedule(session.getUserId());

        if (items.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvSchedule.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvSchedule.setVisibility(View.VISIBLE);
        }

        ScheduleAdapter adapter = new ScheduleAdapter(items, item -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Schedule?")
                    .setMessage(item.getDay() + " - " + item.getSubject())
                    .setPositiveButton("Delete", (d, w) -> {
                        db.deleteScheduleItem(item.getId());
                        loadSchedule();
                    })
                    .setNegativeButton("Cancel", null).show();
        });
        rvSchedule.setAdapter(adapter);
    }

    private void showAddScheduleDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_add_schedule, null);
        Spinner spinnerDay = view.findViewById(R.id.spinner_day);
        EditText etTime = view.findViewById(R.id.et_time_slot);
        EditText etSubject = view.findViewById(R.id.et_schedule_subject);
        EditText etDuration = view.findViewById(R.id.et_duration_mins);
        Spinner spinnerColor = view.findViewById(R.id.spinner_color);

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(dayAdapter);

        String[] colorNames = {"Blue", "Green", "Red", "Orange", "Purple", "Teal"};
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colorNames);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColor.setAdapter(colorAdapter);

        new AlertDialog.Builder(this)
                .setTitle("📅 Add Schedule Slot")
                .setView(view)
                .setPositiveButton("Add", (dialog, which) -> {
                    String subject = etSubject.getText().toString().trim();
                    String time = etTime.getText().toString().trim();
                    if (TextUtils.isEmpty(subject) || TextUtils.isEmpty(time)) {
                        Toast.makeText(this, "Fill subject and time", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int duration = 60;
                    try { duration = Integer.parseInt(etDuration.getText().toString().trim()); }
                    catch (NumberFormatException ignored) {}

                    String[] colors = {"#2979FF", "#00C853", "#FF1744", "#FF6D00", "#AA00FF", "#00BFA5"};
                    String color = colors[spinnerColor.getSelectedItemPosition()];

                    ScheduleItem item = new ScheduleItem(
                            session.getUserId(),
                            spinnerDay.getSelectedItem().toString(),
                            time, subject, duration, color
                    );
                    db.addScheduleItem(item);
                    loadSchedule();
                    Toast.makeText(this, "Schedule added! ✅", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { onBackPressed(); return true; }
        return super.onOptionsItemSelected(item);
    }
}

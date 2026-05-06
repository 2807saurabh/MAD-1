package com.placementpro.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.placementpro.R;
import com.placementpro.database.DatabaseHelper;
import com.placementpro.models.Subject;
import com.placementpro.utils.SessionManager;
import com.placementpro.utils.SubjectDataHelper;
import java.util.ArrayList;
import java.util.List;

public class ProgressActivity extends AppCompatActivity {

    private SessionManager session;
    private DatabaseHelper db;
    private TextView tvOverallPercent, tvTotalTopics, tvDoneTopics, tvTasksDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        session = new SessionManager(this);
        db = DatabaseHelper.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar_progress);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Progress");
        }

        tvOverallPercent = findViewById(R.id.tv_overall_percent);
        tvTotalTopics = findViewById(R.id.tv_total_topics);
        tvDoneTopics = findViewById(R.id.tv_done_topics);
        tvTasksDone = findViewById(R.id.tv_tasks_done);

        loadStats();
        setupBarChart();
    }

    private void loadStats() {
        String userId = session.getUserId();
        int totalTopics = 0;
        for (Subject s : SubjectDataHelper.getAllSubjects()) totalTopics += s.getTotalTopics();
        int doneTopics = db.getTotalCompletedTopics(userId);
        int percent = totalTopics > 0 ? (doneTopics * 100) / totalTopics : 0;

        tvOverallPercent.setText(percent + "%");
        tvTotalTopics.setText("Total Topics: " + totalTopics);
        tvDoneTopics.setText("Completed: " + doneTopics);

        int tasksDone = db.getCompletedTaskCount(userId);
        tvTasksDone.setText(tasksDone + " tasks completed");
    }

    private void setupBarChart() {
        BarChart barChart = findViewById(R.id.bar_chart);
        String userId = session.getUserId();

        List<Subject> subjects = SubjectDataHelper.getAllSubjects();
        // show top 8 subjects
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (int i = 0; i < Math.min(8, subjects.size()); i++) {
            Subject s = subjects.get(i);
            int completed = db.getCompletedTopicsCount(userId, s.getId());
            int total = s.getTotalTopics();
            float percent = total > 0 ? (completed * 100f) / total : 0;
            entries.add(new BarEntry(i, percent));
            labels.add(s.getEmoji() + " " + s.getName().split(" ")[0]);
        }

        BarDataSet dataSet = new BarDataSet(entries, "Progress %");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(10f);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.7f);

        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);
        barChart.getLegend().setTextColor(Color.WHITE);
        barChart.getAxisLeft().setTextColor(Color.WHITE);
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisLeft().setAxisMaximum(100f);
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setTextColor(Color.WHITE);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setLabelRotationAngle(-30f);
        barChart.setBackgroundColor(Color.TRANSPARENT);
        barChart.animateY(1200);
        barChart.invalidate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { onBackPressed(); return true; }
        return super.onOptionsItemSelected(item);
    }
}

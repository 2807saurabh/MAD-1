package com.placementpro.adapters;

import android.graphics.Color;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.placementpro.R;
import com.placementpro.database.DatabaseHelper;
import com.placementpro.models.Subject;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    public interface OnSubjectClick { void onClick(Subject subject); }

    private final List<Subject> subjects;
    private final String userId;
    private final DatabaseHelper db;
    private final OnSubjectClick listener;

    public SubjectAdapter(List<Subject> subjects, String userId, DatabaseHelper db, OnSubjectClick listener) {
        this.subjects = subjects; this.userId = userId; this.db = db; this.listener = listener;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        Subject s = subjects.get(pos);
        h.tvEmoji.setText(s.getEmoji());
        h.tvName.setText(s.getName());
        h.tvCategory.setText(s.getCategory());
        h.tvImportance.setText("⭐ " + s.getImportance() + "% important");

        int completed = db.getCompletedTopicsCount(userId, s.getId());
        int total = s.getTotalTopics();
        int percent = total > 0 ? (completed * 100) / total : 0;

        h.tvTopics.setText(completed + "/" + total + " topics");
        h.progressBar.setProgress(percent);
        h.tvPercent.setText(percent + "%");

        try { h.tvEmoji.setBackgroundColor(Color.parseColor(s.getColor() + "33")); }
        catch (Exception ignored) {}

        h.itemView.setOnClickListener(v -> listener.onClick(s));
    }

    @Override public int getItemCount() { return subjects.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmoji, tvName, tvCategory, tvTopics, tvImportance, tvPercent;
        ProgressBar progressBar;
        ViewHolder(View v) {
            super(v);
            tvEmoji = v.findViewById(R.id.tv_subject_emoji);
            tvName = v.findViewById(R.id.tv_subject_name);
            tvCategory = v.findViewById(R.id.tv_subject_category);
            tvTopics = v.findViewById(R.id.tv_subject_topics);
            tvImportance = v.findViewById(R.id.tv_subject_importance);
            tvPercent = v.findViewById(R.id.tv_subject_percent);
            progressBar = v.findViewById(R.id.pb_subject);
        }
    }
}

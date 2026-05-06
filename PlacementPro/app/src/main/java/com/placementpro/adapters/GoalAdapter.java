package com.placementpro.adapters;

import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.placementpro.R;
import com.placementpro.models.Goal;
import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.ViewHolder> {

    public interface OnProgressUpdate { void onUpdate(Goal goal, int newProgress); }
    public interface OnGoalDelete { void onDelete(Goal goal); }

    private final List<Goal> goals;
    private final OnProgressUpdate updater;
    private final OnGoalDelete deleter;

    public GoalAdapter(List<Goal> goals, OnProgressUpdate updater, OnGoalDelete deleter) {
        this.goals = goals; this.updater = updater; this.deleter = deleter;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goal, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        Goal g = goals.get(pos);
        h.tvTitle.setText(g.isDone() ? "✅ " + g.getTitle() : "🎯 " + g.getTitle());
        h.tvDesc.setText(g.getDescription() != null ? g.getDescription() : "");
        h.tvDate.setText(g.getTargetDate() != null ? "📅 " + g.getTargetDate() : "");
        h.tvProgress.setText(g.getProgress() + "/" + g.getTarget() + " " + g.getUnit());
        h.progressBar.setMax(g.getTarget());
        h.progressBar.setProgress(g.getProgress());
        h.tvPercent.setText(g.getProgressPercent() + "%");

        h.seekBar.setMax(g.getTarget());
        h.seekBar.setProgress(g.getProgress());
        h.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar sb, int val, boolean user) {
                if (user) {
                    h.tvProgress.setText(val + "/" + g.getTarget() + " " + g.getUnit());
                    h.progressBar.setProgress(val);
                    h.tvPercent.setText((g.getTarget() > 0 ? val * 100 / g.getTarget() : 0) + "%");
                }
            }
            public void onStartTrackingTouch(SeekBar sb) {}
            public void onStopTrackingTouch(SeekBar sb) { updater.onUpdate(g, sb.getProgress()); }
        });

        h.btnDelete.setOnClickListener(v -> deleter.onDelete(g));
    }

    @Override public int getItemCount() { return goals.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc, tvDate, tvProgress, tvPercent;
        ProgressBar progressBar;
        SeekBar seekBar;
        ImageButton btnDelete;
        ViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tv_goal_title);
            tvDesc = v.findViewById(R.id.tv_goal_desc);
            tvDate = v.findViewById(R.id.tv_goal_date);
            tvProgress = v.findViewById(R.id.tv_goal_progress);
            tvPercent = v.findViewById(R.id.tv_goal_percent);
            progressBar = v.findViewById(R.id.pb_goal);
            seekBar = v.findViewById(R.id.seek_goal);
            btnDelete = v.findViewById(R.id.btn_delete_goal);
        }
    }
}

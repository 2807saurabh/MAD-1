package com.placementpro.adapters;

import android.graphics.Paint;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.placementpro.R;
import com.placementpro.models.Task;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    public interface OnTaskToggle { void onToggle(Task task); }
    public interface OnTaskDelete { void onDelete(Task task); }

    private final List<Task> tasks;
    private final OnTaskToggle toggle;
    private final OnTaskDelete delete;

    public TaskAdapter(List<Task> tasks, OnTaskToggle toggle, OnTaskDelete delete) {
        this.tasks = tasks; this.toggle = toggle; this.delete = delete;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        Task t = tasks.get(pos);
        h.tvTitle.setText(t.getTitle());
        h.tvDesc.setText(t.getDescription() != null ? t.getDescription() : "");
        h.tvCategory.setText(t.getCategory() != null ? t.getCategory() : "");
        h.tvDue.setText(t.getDueDate() != null ? "📅 " + t.getDueDate() : "");
        h.cbDone.setChecked(t.isDone());

        // Priority badge color
        String priority = t.getPriority() != null ? t.getPriority() : "MEDIUM";
        switch (priority) {
            case "HIGH": h.tvPriority.setText("🔴 HIGH"); break;
            case "LOW":  h.tvPriority.setText("🟢 LOW"); break;
            default:     h.tvPriority.setText("🟡 MED"); break;
        }

        if (t.isDone()) {
            h.tvTitle.setPaintFlags(h.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            h.tvTitle.setAlpha(0.5f);
        } else {
            h.tvTitle.setPaintFlags(h.tvTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            h.tvTitle.setAlpha(1f);
        }

        h.cbDone.setOnClickListener(v -> toggle.onToggle(t));
        h.btnDelete.setOnClickListener(v -> delete.onDelete(t));
    }

    @Override public int getItemCount() { return tasks.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc, tvCategory, tvDue, tvPriority;
        CheckBox cbDone;
        ImageButton btnDelete;
        ViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tv_task_title);
            tvDesc = v.findViewById(R.id.tv_task_desc);
            tvCategory = v.findViewById(R.id.tv_task_category);
            tvDue = v.findViewById(R.id.tv_task_due);
            tvPriority = v.findViewById(R.id.tv_task_priority);
            cbDone = v.findViewById(R.id.cb_task_done);
            btnDelete = v.findViewById(R.id.btn_delete_task);
        }
    }
}

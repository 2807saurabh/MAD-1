package com.placementpro.adapters;

import android.graphics.Color;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.placementpro.R;
import com.placementpro.models.ScheduleItem;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    public interface OnDeleteClick { void onDelete(ScheduleItem item); }

    private final List<ScheduleItem> items;
    private final OnDeleteClick deleter;

    public ScheduleAdapter(List<ScheduleItem> items, OnDeleteClick deleter) {
        this.items = items; this.deleter = deleter;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        ScheduleItem s = items.get(pos);
        h.tvDay.setText(s.getDay());
        h.tvTime.setText("⏰ " + s.getTimeSlot());
        h.tvSubject.setText(s.getSubject());
        h.tvDuration.setText(s.getDuration() + " mins");

        try {
            h.colorStrip.setBackgroundColor(Color.parseColor(s.getColor()));
        } catch (Exception ignored) {
            h.colorStrip.setBackgroundColor(Color.parseColor("#2979FF"));
        }

        h.btnDelete.setOnClickListener(v -> deleter.onDelete(s));
    }

    @Override public int getItemCount() { return items.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay, tvTime, tvSubject, tvDuration;
        View colorStrip;
        ImageButton btnDelete;
        ViewHolder(View v) {
            super(v);
            tvDay = v.findViewById(R.id.tv_schedule_day);
            tvTime = v.findViewById(R.id.tv_schedule_time);
            tvSubject = v.findViewById(R.id.tv_schedule_subject);
            tvDuration = v.findViewById(R.id.tv_schedule_duration);
            colorStrip = v.findViewById(R.id.view_color_strip);
            btnDelete = v.findViewById(R.id.btn_delete_schedule);
        }
    }
}

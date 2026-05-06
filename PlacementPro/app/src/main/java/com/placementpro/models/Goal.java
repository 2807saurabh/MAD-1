package com.placementpro.models;

public class Goal {
    private int id, progress, target;
    private String userId, title, description, unit, targetDate;
    private boolean isDone;

    public Goal() {}
    public Goal(String userId, String title, String description, int target, String unit, String targetDate) {
        this.userId = userId; this.title = title; this.description = description;
        this.target = target; this.unit = unit; this.targetDate = targetDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String u) { userId = u; }
    public String getTitle() { return title; }
    public void setTitle(String t) { title = t; }
    public String getDescription() { return description; }
    public void setDescription(String d) { description = d; }
    public int getProgress() { return progress; }
    public void setProgress(int p) { progress = p; }
    public int getTarget() { return target; }
    public void setTarget(int t) { target = t; }
    public String getUnit() { return unit; }
    public void setUnit(String u) { unit = u; }
    public String getTargetDate() { return targetDate; }
    public void setTargetDate(String d) { targetDate = d; }
    public boolean isDone() { return isDone; }
    public void setDone(boolean d) { isDone = d; }
    public int getProgressPercent() { return target > 0 ? (progress * 100) / target : 0; }
}

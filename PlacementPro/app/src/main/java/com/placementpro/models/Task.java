package com.placementpro.models;

public class Task {
    private int id;
    private String userId, title, description, priority, dueDate, category;
    private boolean isDone;

    public Task() {}
    public Task(String userId, String title, String description, String priority, String dueDate, String category) {
        this.userId = userId; this.title = title; this.description = description;
        this.priority = priority; this.dueDate = dueDate; this.category = category;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public boolean isDone() { return isDone; }
    public void setDone(boolean done) { isDone = done; }
}

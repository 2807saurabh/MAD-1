package com.placementpro.models;

public class TopicProgress {
    private int id;
    private String userId, subjectId, topicName, notes;
    private boolean isCompleted;

    public TopicProgress() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String u) { userId = u; }
    public String getSubjectId() { return subjectId; }
    public void setSubjectId(String s) { subjectId = s; }
    public String getTopicName() { return topicName; }
    public void setTopicName(String t) { topicName = t; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean c) { isCompleted = c; }
    public String getNotes() { return notes; }
    public void setNotes(String n) { notes = n; }
}

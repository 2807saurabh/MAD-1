package com.placementpro.models;

public class ScheduleItem {
    private int id, duration;
    private String userId, day, timeSlot, subject, color;

    public ScheduleItem() {}
    public ScheduleItem(String userId, String day, String timeSlot, String subject, int duration, String color) {
        this.userId = userId; this.day = day; this.timeSlot = timeSlot;
        this.subject = subject; this.duration = duration; this.color = color;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String u) { userId = u; }
    public String getDay() { return day; }
    public void setDay(String d) { day = d; }
    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String t) { timeSlot = t; }
    public String getSubject() { return subject; }
    public void setSubject(String s) { subject = s; }
    public int getDuration() { return duration; }
    public void setDuration(int d) { duration = d; }
    public String getColor() { return color; }
    public void setColor(String c) { color = c; }
}

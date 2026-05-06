package com.placementpro.models;

import java.util.List;

public class Subject {
    private String id, name, category, color, emoji, description;
    private List<String> topics;
    private int importance;

    public Subject(String id, String name, String category, String color, String emoji,
                   List<String> topics, String description, int importance) {
        this.id = id; this.name = name; this.category = category;
        this.color = color; this.emoji = emoji; this.topics = topics;
        this.description = description; this.importance = importance;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getColor() { return color; }
    public String getEmoji() { return emoji; }
    public List<String> getTopics() { return topics; }
    public String getDescription() { return description; }
    public int getImportance() { return importance; }
    public int getTotalTopics() { return topics.size(); }
}

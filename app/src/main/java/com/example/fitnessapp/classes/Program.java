package com.example.fitnessapp.classes;

public class Program {
    private String id;
    private String title;
    private String description;

    public Program(){}

    // Constructor
    public Program(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

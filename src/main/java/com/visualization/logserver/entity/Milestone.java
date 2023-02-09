package com.visualization.logserver.entity;

public class Milestone {
    private Boolean milestone;
    private String description;

    public Milestone(Boolean milestone) {
        this.milestone = milestone;
    }

    public Milestone() {}

    public Milestone(String description, Boolean milestone) {
        this.milestone = milestone;
        this.description = description;
    }

    public Boolean getMilestone() {
        return milestone;
    }

    public void setMilestone(Boolean milestone) {
        this.milestone = milestone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

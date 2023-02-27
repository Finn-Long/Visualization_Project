package com.visualization.logtransformer.entity;

import com.google.cloud.Timestamp;
import com.visualization.logserver.entity.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneralLog {
    private Timestamp timestamp;

    private String source;

    private String id;

    private String name;

    private Boolean isMilestone;

    private String description;

    private String behavior;

    private String result;

    private String error;

    private int executionCount;

    private int errorCount;

    private int duration;

    public GeneralLog(Timestamp timestamp, String source, String id, String name, Boolean isMilestone,
                      String description, String behavior, String result, String error, int executionCount,
                      int errorCount, int duration) {
        this.timestamp = timestamp;
        this.source = source;
        this.id = id;
        this.name = name;
        this.isMilestone = isMilestone;
        this.description = description;
        this.behavior = behavior;
        this.result = result;
        this.error = error;
        this.executionCount = executionCount;
        this.errorCount = errorCount;
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public GeneralLog() {
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getMilestone() {
        return isMilestone;
    }

    public void setMilestone(Boolean milestone) {
        isMilestone = milestone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getExecutionCount() {
        return executionCount;
    }

    public void setExecutionCount(int executionCount) {
        this.executionCount = executionCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }
}

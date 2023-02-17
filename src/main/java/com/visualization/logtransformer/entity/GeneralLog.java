package com.visualization.logtransformer.entity;

import com.google.cloud.Timestamp;
import com.visualization.logserver.entity.Content;
import com.visualization.logserver.entity.Milestone;
import com.visualization.logserver.entity.Student;
import java.text.ParseException;

public class GeneralLog {
    private Student student;
    private Timestamp timestamp;
    private Milestone milestone;
    private String source;
    private Content content;

    public GeneralLog(Student student, Timestamp timestamp, Milestone milestone, String source, Content content) throws ParseException {
        this.student = student;
        this.timestamp = timestamp;
        this.milestone = milestone;
        this.source = source;
        this.content = content;
    }

    public GeneralLog() {
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}

package com.visualization.logserver.entity;
import com.google.cloud.Timestamp;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Log {
    private Student student;
    private Timestamp timestamp;
    private Milestone milestone;
    private String source;
    private Content content;

    public Log(Student student, String timestampString, Milestone milestone, String source, Content content) throws ParseException {
        this.student = student;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        Date date = simpleDateFormat.parse(timestampString);
        Timestamp timestamp = Timestamp.ofTimeSecondsAndNanos(date.getTime() / 1000,
                (int) ((date.getTime() % 1000) * 1000000));
        this.timestamp = timestamp;
        this.milestone = milestone;
        this.source = source;
        this.content = content;
    }

    public Log() {
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getTimestamp() {
        return timestamp.toString();
    }


//    public void setTimestamp(String timestampString) throws ParseException {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
//        Date date = simpleDateFormat.parse(timestampString);
//        Timestamp timestamp1 = Timestamp.ofTimeSecondsAndNanos(date.getTime() / 1000,
//                (int) ((date.getTime() % 1000) * 1000000));
//        this.timestamp = timestamp1;
//    }

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

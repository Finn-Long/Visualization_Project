package com.visualization.logserver.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.Timestamp;
import com.visualization.logtransformer.entity.GeneralLog;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Log extends GeneralLog {

    public Log(String timestampString, String source, String id, String name, Boolean isMilestone,
               String description, String behavior, String result, String error, int executionCount, int errorCount, int duration) throws ParseException {
        super(toTimeStamp(timestampString),source,id,name,isMilestone,description,behavior,result,error,executionCount,errorCount, duration);
    }

    public Log(){
        super();
    }

    private static Timestamp toTimeStamp(String timestampString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        Date date = simpleDateFormat.parse(timestampString);
        Timestamp timestamp = Timestamp.ofTimeSecondsAndNanos(date.getTime() / 1000,
                (int) ((date.getTime() % 1000) * 1000000));
        return timestamp;
    }

    @JsonProperty("timestamp")
    public String getTimeStampString() {
        return super.getTimestamp().toString();
    }
}

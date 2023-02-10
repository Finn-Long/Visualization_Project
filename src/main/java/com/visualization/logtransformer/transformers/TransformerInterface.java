package com.visualization.logtransformer.transformers;

import com.google.cloud.Timestamp;
import com.google.gson.JsonObject;
import com.visualization.logserver.entity.Content;
import com.visualization.logserver.entity.Milestone;
import com.visualization.logserver.entity.Student;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

public interface TransformerInterface {
    public void setLog() throws IOException, ExecutionException, InterruptedException, ParseException;
    public void addLogHelper(Student student, Milestone milestone, Content content, String source, Timestamp timestamp) throws ParseException;

    public String getNamePairHelper(String id) throws ExecutionException, InterruptedException;
    public void setNamePairHelper(String id, String name);
    public String[] getRandomNamesHelper(int num) throws IOException;
    public JsonObject getLogJsonHelper();
}

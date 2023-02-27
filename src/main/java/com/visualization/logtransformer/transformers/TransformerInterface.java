package com.visualization.logtransformer.transformers;

import com.google.cloud.Timestamp;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

public interface TransformerInterface {
    public void setLog() throws IOException, ExecutionException, InterruptedException, ParseException;
    public void addLogHelper(Timestamp timestamp, String source, String id, String name, Boolean isMilestone,
                             String description, String behavior, String result, String error, int executionCount,
                             int errorCount, int duration) throws ParseException;

    public String getNamePairHelper(String id) throws ExecutionException, InterruptedException;
    public void setNamePairHelper(String id, String name);
    public String[] getRandomNamesHelper(int num) throws IOException;
    public JsonObject getLogJsonHelper();
}

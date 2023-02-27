package com.visualization.logserver.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.visualization.logserver.entity.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@Service
public class LogServiceLocal {
    private static final String fileName = "Logs.json";

    public String getLogs() {
        JsonArray logArray = getLogJsonHelper(fileName);
        return logArray.toString();
    }

    public String getByStudentName(String name) throws FileNotFoundException {
        return getHelper("name", name);
    }

    public String getByStudentId(String id) {
        return getHelper("id", id);
    }

    public String getAllMilestones() {
        JsonArray logArray = getLogJsonHelper(fileName);
        JsonArray output = new JsonArray();
        for (int i = 0; i < logArray.size(); i ++) {
            JsonObject log = logArray.get(i).getAsJsonObject();
            Boolean isMilestone = log.get("milestone").getAsBoolean();
            if (isMilestone) {
                output.add(log);
            }
        }
        return output.toString();
    }

    public String getAllMilestoneByName(String name) {
        JsonArray logArray = getLogJsonHelper(fileName);
        JsonArray output = new JsonArray();
        for (int i = 0; i < logArray.size(); i ++) {
            JsonObject log = logArray.get(i).getAsJsonObject();
            Boolean isMilestone = log.get("milestone").getAsBoolean();
            if (isMilestone) {
                String studentName = log.get("name").getAsString();
                if (studentName.equals(name)) {
                    output.add(log);
                }
            }
        }
        return output.toString();
    }

    public String getHelper(String field, String givenValue) {
        JsonArray logArray = getLogJsonHelper(fileName);
        JsonArray output = new JsonArray();
        for (int i = 0; i < logArray.size(); i ++) {
            JsonObject log = logArray.get(i).getAsJsonObject();
            String fieldValue = log.get(field).getAsString();
            if (fieldValue.equals(givenValue)) {
                output.add(log);
            }
        }
        return output.toString();
    }

    public JsonArray getLogJsonHelper(String fileName) {
        File file = new File("./" + fileName);
        try (FileReader reader = new FileReader(file)) {
            JsonArray obj = JsonParser.parseReader(reader).getAsJsonArray();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

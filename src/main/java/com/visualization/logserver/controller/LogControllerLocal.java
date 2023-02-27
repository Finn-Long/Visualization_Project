package com.visualization.logserver.controller;

import com.google.gson.JsonArray;
import com.visualization.logserver.service.LogServiceLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("local")
public class LogControllerLocal {
    @Autowired
    private LogServiceLocal logServiceLocal;

    @GetMapping("/logs/get_all_logs")
    public String getAllLogs() {
        return logServiceLocal.getLogs();
    }

    @GetMapping("/logs/{name}")
    public String getLogByStudentName(@PathVariable String name) throws FileNotFoundException {
        return logServiceLocal.getByStudentName(name);
    }

    @GetMapping("/logs/id/{id}")
    public String getLogByStudentId(@PathVariable String id) {
        return logServiceLocal.getByStudentId(id);
    }

    @GetMapping("/logs/milestones")
    public String getMilestones() {
        return logServiceLocal.getAllMilestones();
    }

    @GetMapping("/logs/{name}/milestones")
    public String getMilestonesByName(@PathVariable String name) {
        return logServiceLocal.getAllMilestoneByName(name);
    }
}

package com.visualization.logserver.controller;

import com.visualization.logserver.entity.Log;
import com.visualization.logserver.service.LogService;
import com.visualization.logserver.service.LogServiceLocal;
import com.visualization.logtransformer.entity.GeneralLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class LogController {

    @Autowired
    private LogService logService;


    @GetMapping("logs/get_all_logs")
    public List<Log> getLogTest() throws ExecutionException, InterruptedException {
        return logService.getLogs();
    }

//    @PostMapping("/logs")
//    public String postLog(@RequestBody GeneralLog log) throws ExecutionException, InterruptedException {
//        return logService.saveLog(log);
//    }

    @GetMapping("/logs/{name}")
    public List<Log> getLogByStudentName(@PathVariable String name) throws ExecutionException, InterruptedException {
        return logService.getByStudentName(name);
    }

    @GetMapping("/logs/id/{id}")
    public List<Log> getLogByStudentId(@PathVariable String id) throws ExecutionException, InterruptedException {
        return logService.getByStudentId(id);
    }

    @GetMapping("/logs/milestones")
    public List<Log> getMilestones() throws ExecutionException, InterruptedException {
        return logService.getAllMilestone();
    }

    @GetMapping("/logs/{name}/milestones")
    public List<Log> getMilestonesByName(@PathVariable String name) throws ExecutionException, InterruptedException {
        return logService.getAllMilestoneByName(name);
    }

    @DeleteMapping("/logs")
    public String deleteLog() throws ExecutionException, InterruptedException {
        return logService.deleteAllLogs();
    }
}
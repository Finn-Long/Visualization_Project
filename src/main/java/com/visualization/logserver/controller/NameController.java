package com.visualization.logserver.controller;

import com.visualization.logserver.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class NameController {

    @Autowired
    private NameService nameService;

    @GetMapping("/names")
    public List<String> getAllNames() throws ExecutionException, InterruptedException {
        return nameService.getNames();
    }

    @DeleteMapping
    public String deleteName() {
        return nameService.deleteAllNamePairs();
    }
}

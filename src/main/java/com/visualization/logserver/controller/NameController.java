package com.visualization.logserver.controller;

import com.visualization.logserver.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/names")
    public String deleteName() {
        return nameService.deleteAllNamePairs();
    }
}

package com.example.adl.controller;

import com.example.adl.bean.Lab;
import com.example.adl.service.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实验室 REST 接口。
 */
@RestController
@RequestMapping("/lab")
public class LabController {
    @Autowired
    private LabService service;

    @GetMapping
    public List<Lab> findAll() {
        return service.findAll();
    }

    @GetMapping("/{labId}")
    public Lab findById(@PathVariable("labId") Integer labId) {
        return service.findById(labId);
    }

    @PostMapping
    public Lab add(@RequestBody Lab lab) {
        return service.add(lab);
    }

    @PutMapping("/{labId}")
    public boolean update(@PathVariable("labId") Integer labId, @RequestBody Lab lab) {
        return service.update(labId, lab);
    }

    @DeleteMapping("/{labId}")
    public boolean deleteById(@PathVariable("labId") Integer labId) {
        return service.deleteById(labId);
    }
}

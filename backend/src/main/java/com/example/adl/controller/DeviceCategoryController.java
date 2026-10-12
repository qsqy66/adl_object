package com.example.adl.controller;

import com.example.adl.bean.DeviceCategory;
import com.example.adl.service.DeviceCategoryService;
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
 * 设备分类 REST 接口。
 */
@RestController
@RequestMapping("/category")
public class DeviceCategoryController {
    @Autowired
    private DeviceCategoryService service;

    @GetMapping
    public List<DeviceCategory> findAll() {
        return service.findAll();
    }

    @GetMapping("/{categoryId}")
    public DeviceCategory findById(@PathVariable("categoryId") Integer categoryId) {
        return service.findById(categoryId);
    }

    @PostMapping
    public DeviceCategory add(@RequestBody DeviceCategory category) {
        return service.add(category);
    }

    @PutMapping("/{categoryId}")
    public boolean update(@PathVariable("categoryId") Integer categoryId,
                          @RequestBody DeviceCategory category) {
        return service.update(categoryId, category);
    }

    @DeleteMapping("/{categoryId}")
    public boolean deleteById(@PathVariable("categoryId") Integer categoryId) {
        return service.deleteById(categoryId);
    }
}

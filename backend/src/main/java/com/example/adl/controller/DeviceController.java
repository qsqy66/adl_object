package com.example.adl.controller;

import com.example.adl.bean.Device;
import com.example.adl.service.DeviceService;
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
 * 设备 REST 接口。
 */
@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    private DeviceService service;

    @GetMapping
    public List<Device> findAll() {
        return service.findAll();
    }

    @GetMapping("/{deviceId}")
    public Device findById(@PathVariable("deviceId") Integer deviceId) {
        return service.findById(deviceId);
    }

    @PostMapping
    public Device add(@RequestBody Device device) {
        return service.add(device);
    }

    @PutMapping("/{deviceId}")
    public boolean update(@PathVariable("deviceId") Integer deviceId,
                          @RequestBody Device device) {
        return service.update(deviceId, device);
    }

    @DeleteMapping("/{deviceId}")
    public boolean deleteById(@PathVariable("deviceId") Integer deviceId) {
        return service.deleteById(deviceId);
    }
}

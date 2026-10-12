package com.example.adl.service;

import com.example.adl.bean.Device;

import java.util.List;

/**
 * 设备业务接口。
 */
public interface DeviceService {
    List<Device> findAll();
    Device findById(Integer deviceId);
    Device add(Device device);
    boolean update(Integer deviceId, Device device);
    boolean deleteById(Integer deviceId);
}

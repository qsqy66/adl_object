package com.example.adl.service.Impl;

import com.example.adl.bean.Device;
import com.example.adl.mapper.DeviceMapper;
import com.example.adl.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备业务实现。
 */
@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceMapper mapper;

    @Override
    public List<Device> findAll() {
        return mapper.findAll();
    }

    @Override
    public Device findById(Integer deviceId) {
        return mapper.findById(deviceId);
    }

    @Override
    public Device add(Device device) {
        mapper.insert(device);
        return device;
    }

    @Override
    public boolean update(Integer deviceId, Device device) {
        device.setDeviceId(deviceId);
        return mapper.update(device) > 0;
    }

    @Override
    public boolean deleteById(Integer deviceId) {
        return mapper.deleteById(deviceId) > 0;
    }
}

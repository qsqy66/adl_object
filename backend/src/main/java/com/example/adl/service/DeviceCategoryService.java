package com.example.adl.service;

import com.example.adl.bean.DeviceCategory;

import java.util.List;

/**
 * 设备分类业务接口。
 */
public interface DeviceCategoryService {
    List<DeviceCategory> findAll();
    DeviceCategory findById(Integer categoryId);
    DeviceCategory add(DeviceCategory category);
    boolean update(Integer categoryId, DeviceCategory category);
    boolean deleteById(Integer categoryId);
}

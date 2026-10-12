package com.example.adl.service.Impl;

import com.example.adl.bean.DeviceCategory;
import com.example.adl.mapper.DeviceCategoryMapper;
import com.example.adl.service.DeviceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备分类业务实现。
 */
@Service
public class DeviceCategoryServiceImpl implements DeviceCategoryService {
    @Autowired
    private DeviceCategoryMapper mapper;

    @Override
    public List<DeviceCategory> findAll() {
        return mapper.findAll();
    }

    @Override
    public DeviceCategory findById(Integer categoryId) {
        return mapper.findById(categoryId);
    }

    @Override
    public DeviceCategory add(DeviceCategory category) {
        mapper.insert(category);
        return category;
    }

    @Override
    public boolean update(Integer categoryId, DeviceCategory category) {
        category.setCategoryId(categoryId);
        return mapper.update(category) > 0;
    }

    @Override
    public boolean deleteById(Integer categoryId) {
        return mapper.deleteById(categoryId) > 0;
    }
}

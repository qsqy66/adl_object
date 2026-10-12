package com.example.adl.service.Impl;

import com.example.adl.bean.Lab;
import com.example.adl.mapper.LabMapper;
import com.example.adl.service.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实验室业务实现。
 */
@Service
public class LabServiceImpl implements LabService {
    @Autowired
    private LabMapper mapper;

    @Override
    public List<Lab> findAll() {
        return mapper.findAll();
    }

    @Override
    public Lab findById(Integer labId) {
        return mapper.findById(labId);
    }

    @Override
    public Lab add(Lab lab) {
        mapper.insert(lab);
        return lab;
    }

    @Override
    public boolean update(Integer labId, Lab lab) {
        lab.setLabId(labId);
        return mapper.update(lab) > 0;
    }

    @Override
    public boolean deleteById(Integer labId) {
        return mapper.deleteById(labId) > 0;
    }
}

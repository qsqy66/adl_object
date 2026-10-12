package com.example.adl.service;

import com.example.adl.bean.Lab;

import java.util.List;

/**
 * 实验室业务接口。
 */
public interface LabService {
    List<Lab> findAll();
    Lab findById(Integer labId);
    Lab add(Lab lab);
    boolean update(Integer labId, Lab lab);
    boolean deleteById(Integer labId);
}

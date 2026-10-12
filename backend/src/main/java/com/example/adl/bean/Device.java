package com.example.adl.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 设备实体。categoryName 和 labName 用于列表查询时展示关联名称。
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Device {
    private Integer deviceId;
    private String deviceName;
    private Integer categoryId;
    private Integer labId;
    private String deviceSpec;
    private String deviceStatus;
    private LocalDate buyDate;
    private BigDecimal price;

    private String categoryName;
    private String labName;
}

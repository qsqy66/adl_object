package com.example.adl.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 设备分类实体。
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceCategory {
    private Integer categoryId;
    private String categoryName;
    private String categoryDesc;
}

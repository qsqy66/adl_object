package com.example.adl.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 实验室实体。
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Lab {
    private Integer labId;
    private String labName;
    private String labLocation;
    private String labRemark;
}

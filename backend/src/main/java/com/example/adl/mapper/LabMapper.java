package com.example.adl.mapper;

import com.example.adl.bean.Lab;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 实验室数据访问接口。
 */
@Mapper
public interface LabMapper {

    @Select("SELECT lab_id AS labId, lab_name AS labName, lab_location AS labLocation, " +
            "lab_remark AS labRemark FROM lab ORDER BY lab_id")
    List<Lab> findAll();

    @Select("SELECT lab_id AS labId, lab_name AS labName, lab_location AS labLocation, " +
            "lab_remark AS labRemark FROM lab WHERE lab_id = #{labId}")
    Lab findById(Integer labId);

    @Insert("INSERT INTO lab(lab_name, lab_location, lab_remark) " +
            "VALUES (#{labName}, #{labLocation}, #{labRemark})")
    @Options(useGeneratedKeys = true, keyProperty = "labId", keyColumn = "lab_id")
    int insert(Lab lab);

    @Update("UPDATE lab SET lab_name = #{labName}, lab_location = #{labLocation}, " +
            "lab_remark = #{labRemark} WHERE lab_id = #{labId}")
    int update(Lab lab);

    @Delete("DELETE FROM lab WHERE lab_id = #{labId}")
    int deleteById(@Param("labId") Integer labId);
}

package com.example.adl.mapper;

import com.example.adl.bean.DeviceCategory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 设备分类数据访问接口。
 */
@Mapper
public interface DeviceCategoryMapper {

    @Select("SELECT category_id AS categoryId, category_name AS categoryName, " +
            "category_desc AS categoryDesc FROM device_category ORDER BY category_id")
    List<DeviceCategory> findAll();

    @Select("SELECT category_id AS categoryId, category_name AS categoryName, " +
            "category_desc AS categoryDesc FROM device_category WHERE category_id = #{categoryId}")
    DeviceCategory findById(Integer categoryId);

    @Insert("INSERT INTO device_category(category_name, category_desc) " +
            "VALUES (#{categoryName}, #{categoryDesc})")
    @Options(useGeneratedKeys = true, keyProperty = "categoryId", keyColumn = "category_id")
    int insert(DeviceCategory category);

    @Update("UPDATE device_category SET category_name = #{categoryName}, " +
            "category_desc = #{categoryDesc} WHERE category_id = #{categoryId}")
    int update(DeviceCategory category);

    @Delete("DELETE FROM device_category WHERE category_id = #{categoryId}")
    int deleteById(@Param("categoryId") Integer categoryId);
}

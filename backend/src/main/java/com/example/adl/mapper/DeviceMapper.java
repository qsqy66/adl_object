package com.example.adl.mapper;

import com.example.adl.bean.Device;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 设备数据访问接口。
 */
@Mapper
public interface DeviceMapper {

    // 联表查询分类名称和实验室名称，便于前端直接展示。
    @Select("SELECT d.device_id AS deviceId, d.device_name AS deviceName, " +
            "d.category_id AS categoryId, d.lab_id AS labId, d.device_spec AS deviceSpec, " +
            "d.device_status AS deviceStatus, d.buy_date AS buyDate, d.price, " +
            "c.category_name AS categoryName, l.lab_name AS labName " +
            "FROM device d " +
            "JOIN device_category c ON d.category_id = c.category_id " +
            "JOIN lab l ON d.lab_id = l.lab_id ORDER BY d.device_id")
    List<Device> findAll();

    @Select("SELECT d.device_id AS deviceId, d.device_name AS deviceName, " +
            "d.category_id AS categoryId, d.lab_id AS labId, d.device_spec AS deviceSpec, " +
            "d.device_status AS deviceStatus, d.buy_date AS buyDate, d.price, " +
            "c.category_name AS categoryName, l.lab_name AS labName " +
            "FROM device d " +
            "JOIN device_category c ON d.category_id = c.category_id " +
            "JOIN lab l ON d.lab_id = l.lab_id WHERE d.device_id = #{deviceId}")
    Device findById(Integer deviceId);

    @Insert("INSERT INTO device(device_name, category_id, lab_id, device_spec, " +
            "device_status, buy_date, price) VALUES (#{deviceName}, #{categoryId}, " +
            "#{labId}, #{deviceSpec}, #{deviceStatus}, #{buyDate}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "deviceId", keyColumn = "device_id")
    int insert(Device device);

    @Update("UPDATE device SET device_name = #{deviceName}, category_id = #{categoryId}, " +
            "lab_id = #{labId}, device_spec = #{deviceSpec}, device_status = #{deviceStatus}, " +
            "buy_date = #{buyDate}, price = #{price} WHERE device_id = #{deviceId}")
    int update(Device device);

    @Delete("DELETE FROM device WHERE device_id = #{deviceId}")
    int deleteById(@Param("deviceId") Integer deviceId);
}

package edu.whut.yangxingrui.coffeeordersystem.mapper;

import edu.whut.yangxingrui.coffeeordersystem.model.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ManagerMapper {

    @Select("select * from t_manager")
    List<Manager> findAll();

    @Select("select * from t_manager where name=#{name}")
    Manager selectByName(@Param("name") String name);
}

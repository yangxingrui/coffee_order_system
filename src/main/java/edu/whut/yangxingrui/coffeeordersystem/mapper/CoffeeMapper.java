package edu.whut.yangxingrui.coffeeordersystem.mapper;

import edu.whut.yangxingrui.coffeeordersystem.model.Coffee;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CoffeeMapper {
    @Insert("insert into t_coffee (name, price, create_time, update_time)"
            + "values (#{name}, #{price}, now(), now())")
    @Options(useGeneratedKeys = true)
    int save(Coffee coffee);

    @Select("select * from t_coffee")
    List<Coffee> findAll();

    @Update("update t_coffee set name = #{name},update_time = now() where id = #{id}")
    int update(@Param("id") Long id,String name);
}

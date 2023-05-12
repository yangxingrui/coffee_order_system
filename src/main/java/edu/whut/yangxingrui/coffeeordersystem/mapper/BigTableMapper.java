package edu.whut.yangxingrui.coffeeordersystem.mapper;

import edu.whut.yangxingrui.coffeeordersystem.model.BigTable;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BigTableMapper {
    @Select("SELECT a.id,a.create_time,a.update_time,a.customer,GROUP_CONCAT(a.coffee_id SEPARATOR ',') coffeeIdList,GROUP_CONCAT(a.name SEPARATOR ',') nameList,GROUP_CONCAT(a.price SEPARATOR ',') priceList from (select t_order.id,t_order.create_time,t_order.update_time,customer,t_coffee.id coffee_id,name,price from t_order,t_coffee,t_order_coffee where t_order.id = t_order_coffee.order_id and t_coffee.id = t_order_coffee.coffee_id) as a group BY a.id")
    @Results({
            @Result(column = "id",property = "orderId"),
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "update_time",property = "updateTime"),
            @Result(column = "customer",property = "customer"),
            @Result(column = "coffeeIdList",property = "coffeeId"),
            @Result(column = "nameList",property = "name"),
            @Result(column = "priceList",property = "price"),
    })
    List<BigTable> getAllOrder();
}

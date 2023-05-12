package edu.whut.yangxingrui.coffeeordersystem.mapper;

import edu.whut.yangxingrui.coffeeordersystem.model.Order;
import edu.whut.yangxingrui.coffeeordersystem.model.OrderCoffee;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface OrderMapper {

    @Insert("insert into t_order (create_time, update_time, customer)"
            + "values (now(), now(), #{customer})")
    @Options(useGeneratedKeys = true)
    int insertOrder(Order order);

    @Insert("insert into t_order_coffee (order_id, coffee_id)"
            + "values (#{order_id}, #{coffee_id})")
    @Options(useGeneratedKeys = true)
    int insertOrderCoffee(OrderCoffee orderCoffee);

    @Delete("delete from t_order where id = #{id}")
    int deleteOrder(Long id);
}

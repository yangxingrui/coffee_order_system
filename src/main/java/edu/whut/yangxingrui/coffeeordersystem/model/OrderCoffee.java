package edu.whut.yangxingrui.coffeeordersystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCoffee {
    private Long id;
    private Long order_id;
    private Long coffee_id;
}

package edu.whut.yangxingrui.coffeeordersystem.Controller;

import edu.whut.yangxingrui.coffeeordersystem.handler.EncryptHandler;
import edu.whut.yangxingrui.coffeeordersystem.mapper.BigTableMapper;
import edu.whut.yangxingrui.coffeeordersystem.mapper.OrderMapper;
import edu.whut.yangxingrui.coffeeordersystem.model.BigTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Slf4j
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired(required = false)
    private OrderMapper orderMapper;
    @Autowired(required = false)
    private BigTableMapper bigTableMapper;

    @ModelAttribute
    public List<BigTable> orderList() {
        List<BigTable> bigTables = bigTableMapper.getAllOrder();
        EncryptHandler encryptTypeHandler = new EncryptHandler();
        for (BigTable bigTable : bigTables) {
            bigTable.setCustomer(encryptTypeHandler.decrypt(bigTable.getCustomer()));
            String[] pricelist = bigTable.getPrice().split(",");
            String newpricelist = "";
            Double newprice = 0.0;
            for (String s : pricelist) {
                newprice = Double.parseDouble(String.valueOf(s)) / 100.0;
                newpricelist += newprice + ",";
            }
            newpricelist = newpricelist.substring(0,newpricelist.length()-1);

            bigTable.setPrice(newpricelist);
        }
        return bigTables;
    }
    @GetMapping(path = "")
    public ModelAndView showCreateForm() {
        return new ModelAndView("order");
    }

    @GetMapping("/delete/{id}")
    public String orderDelete(@PathVariable Long id) {
        orderMapper.deleteOrder(id);
        return "redirect:/order/";
    }
}

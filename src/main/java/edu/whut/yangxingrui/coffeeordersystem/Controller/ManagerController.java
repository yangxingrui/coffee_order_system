package edu.whut.yangxingrui.coffeeordersystem.Controller;

import edu.whut.yangxingrui.coffeeordersystem.handler.EncryptHandler;
import edu.whut.yangxingrui.coffeeordersystem.mapper.BigTableMapper;
import edu.whut.yangxingrui.coffeeordersystem.mapper.CoffeeMapper;
import edu.whut.yangxingrui.coffeeordersystem.model.BigTable;
import edu.whut.yangxingrui.coffeeordersystem.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

@Slf4j
@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private CoffeeMapper coffeeMapper;
    @Autowired
    private BigTableMapper bigTableMapper;
    @ModelAttribute
    public List<Coffee> coffeeList() {
        return coffeeMapper.findAll();
    }

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
        log.info("管理员登陆成功");
        return new ModelAndView("manager");
    }

    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return "redirect:/login";
    }

}

package edu.whut.yangxingrui.coffeeordersystem.Controller;
import edu.whut.yangxingrui.coffeeordersystem.handler.EncryptHandler;
import edu.whut.yangxingrui.coffeeordersystem.mapper.CoffeeMapper;
import edu.whut.yangxingrui.coffeeordersystem.mapper.OrderMapper;
import edu.whut.yangxingrui.coffeeordersystem.model.Coffee;
import edu.whut.yangxingrui.coffeeordersystem.model.Order;
import edu.whut.yangxingrui.coffeeordersystem.model.OrderCoffee;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/coffee")
public class CoffeeController {
    @Autowired(required = false)
    private CoffeeMapper coffeeMapper;
    @Autowired(required = false)
    private OrderMapper orderMapper;
//    @Autowired
//    private StringEncryptor codeSheepEncryptorBean;
    @ModelAttribute
    public List<Coffee> coffeeList() {

//        String mysqlOriginPswd = "Yangxr9816";
//        // 加密
//        String mysqlEncryptedPswd = decrypt(mysqlOriginPswd);
//
//        System.out.println( "====================================" );
//        System.out.println( "MySQL原始明文密码加密后的结果为：" + mysqlEncryptedPswd );
        return coffeeMapper.findAll();
    }

//    private String encrypt( String originPassord ) {
//        String encryptStr = codeSheepEncryptorBean.encrypt( originPassord );
//        return encryptStr;
//    }
//
//    private String decrypt( String encryptedPassword ) {
//        String decryptStr = codeSheepEncryptorBean.decrypt( encryptedPassword );
//        return decryptStr;
//    }

    @GetMapping(path = "")
    public ModelAndView showCreateForm() {
        return new ModelAndView("coffee");
    }

    @PostMapping(path = "/")    //订单提交到此处
    public String createOrder(@RequestParam("customer") String customer,@RequestParam("items") Long[] c) {
        log.info("Receive new Order Customer:{} CoffeeId:{}", customer, c);
        Order order = Order.builder().customer(new EncryptHandler().encrypt(customer)).build();
        orderMapper.insertOrder(order);
        log.info("New Order: {}", order);
        for(int i=0;i<c.length;i++){
            OrderCoffee oc = OrderCoffee.builder().order_id(order.getId()).coffee_id(c[i]).build();
            orderMapper.insertOrderCoffee(oc);
            log.info("New OrderCoffee: {}", oc);
        }
        return "redirect:/order/" ;
    }

    @PostMapping(path = "/save")
    public String save(String name, double price){
        try{
            Money money = Money.of(CurrencyUnit.of("CNY"), price);
            Coffee coffee = Coffee.builder().name(name).price(money).build();
            coffeeMapper.save(coffee);
        }
        catch(Exception e){
            log.info("输入不符合人民币规则");
            return "redirect:/manager/" ;
        }
        return "redirect:/manager/" ;
    }

    @PostMapping(path = "/update/{id}")
    public String update(@PathVariable Long id,String name){
        coffeeMapper.update(id,name);
        return "redirect:/coffee/" ;
    }

//    @GetMapping(path = "/test")
//    @ResponseBody
//    public void test(){
//        System.out.println("test ok!!!");
//        return ;
//    }
}

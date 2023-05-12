package edu.whut.yangxingrui.coffeeordersystem.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    MyRealm myRealm() {
        return new MyRealm();
    }

    @Bean
    DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myRealm());
        return defaultWebSecurityManager;
    }

    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager()); // 指定会话管理器SecurityManager
        bean.setLoginUrl("/login"); // 指定登录页面
        bean.setUnauthorizedUrl("/templates/error.html");  //未授权界面
        bean.setSuccessUrl("/manager"); // 指定登录成功页面,不起效
        // Map中配置了路径拦截规则，注意：要有序
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/coffee", "anon");
        map.put("/order", "anon");
        map.put("/error", "anon");
        map.put("/**", "authc");
        bean.setFilterChainDefinitionMap(map);
        return bean;
    }
}


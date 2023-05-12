package edu.whut.yangxingrui.coffeeordersystem.config;

import edu.whut.yangxingrui.coffeeordersystem.mapper.ManagerMapper;
import edu.whut.yangxingrui.coffeeordersystem.model.Manager;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private ManagerMapper managerMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected SimpleAuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        //1.把AuthenticationToken转换为UsernamePasswordToken
        UsernamePasswordToken userToken = (UsernamePasswordToken) authcToken;

        //2.从UsernamePasswordToken中获取username
        String username = userToken.getUsername();

        Manager manager = managerMapper.selectByName(username);
        if (manager == null) {
            throw new UnknownAccountException(); //没有找到账号
        }

        //交给AuthenticationRealm不加密
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                manager.getName(), //用户名
                //passwordHelper.encryptPassword(user.getPassword()), //密码
                manager.getPassword(),
                ByteSource.Util.bytes(manager.getName()),
                getName() //realm name
        );
        //System.out.println("***********加密后的密码(1231234)："+passwordHelper.encryptPassword(user.getName(),"1231234"));
        return authenticationInfo;
    }
}


package cn.zrf.shirodemo.service.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;

public class WeixinRealm extends AuthenticatingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("WeixinRealm类里这个doGetAuthenticationInfo方法，就是用来实现微信登录的方法");
        AuthenticationInfo info = new SimpleAuthenticationInfo("weixin445mfgg", "123", null, getName());
        return info;
    }
}

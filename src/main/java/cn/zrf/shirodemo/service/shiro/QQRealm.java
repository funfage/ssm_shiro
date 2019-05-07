package cn.zrf.shirodemo.service.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;

public class QQRealm extends AuthenticatingRealm {

    /**
     * 专门用来处理登录认证的方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("QQRealm类里这个doGetAuthenticationInfo方法，就是用来实现QQ登录的方法");
        AuthenticationInfo info = new SimpleAuthenticationInfo("qq34fgf", "123", null, getName());
        return info;
    }
}

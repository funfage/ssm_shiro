package cn.zrf.shirodemo.service.shiro;

import cn.zrf.shirodemo.dao.UserDao;
import cn.zrf.shirodemo.model.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 如果只需要登录认证，则继承AuthenticatingRealm即可
 */
public class ShiroRealm extends AuthenticatingRealm {

    @Autowired
    private UserDao userDao;

    /**
     * 真正在做项目的时候，登录的验证工作，doGetAuthenticationInfo方法中来实现的
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
         //1，token把参数强转为UsernamePasswordToken
        UsernamePasswordToken token1 = (UsernamePasswordToken) token;
        //2，从token1获取表单提交过来的用户名
        String username = token1.getUsername();
        //3，从数据库中来查询有没用户名是username的用户记录
        User user = null;
        try {
            user = userDao.getUserByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //4，根据user对象的具体情况，可以抛出shiro定义的异常信息
        if(user == null){
            throw new UnknownAccountException("无此用户");
        }
        if(user.getStatus() == 0){
            throw new LockedAccountException("用户已被管理员禁用");
        }
        //5，进一步的让shiro来帮我们判断用户表单填写并传来的密码是否正确
        //使用username充当盐值
        ByteSource salt = ByteSource.Util.bytes(username);
        AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());
        //principals: 可以是用户名，也可以是登录用户的user对象
        //hashedCredentials: 从数据库中获取的用户密码
        //credentialsSalt:密码加密的盐值
        //RealName:ShiroRealm的名字
        return info;
    }

    public static void main(String[] args) {
        Object rs = new SimpleHash("MD5", "202cb962ac59075b964b07152d234b70", "admin",1024);
        System.out.println(rs);
    }
}

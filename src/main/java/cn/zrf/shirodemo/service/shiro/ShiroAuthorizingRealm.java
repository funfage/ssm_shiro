package cn.zrf.shirodemo.service.shiro;

import cn.zrf.shirodemo.dao.PermissionDao;
import cn.zrf.shirodemo.dao.RoleDao;
import cn.zrf.shirodemo.dao.UserDao;
import cn.zrf.shirodemo.model.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * 如果需要授权认证，则要继承AuthorizingRealm
 */
public class ShiroAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;


    /**
     * 在shiro中专门用来做授权认证的方法
     * @param principals
     * 处理授权认证的方法带来的参数PrincipalCollection principals的三个结论：
     *         1，传进来的是登录成功以后的用户信息，与做登录认证的方法doGetAuthenticationInfo中的AuthenticationInfo对象第一个参数密切相关
     *         2，由于登录认证可能是多个realm对象，所以可能传来多个用户的信息，这个参数本质来说是一个集合，可能有多个元素
     *         3，参数的集合里边的元素的顺序，受realm在spring.xml中配置的顺序影响
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        /*System.out.println(principals);
        System.out.println(principals.getPrimaryPrincipal());
        System.out.println(principals.oneByType(User.class));
        System.out.println(principals.asList());*/
        //1， 从参数principals获取当前登录成功后的用户信息
        User user = principals.oneByType(User.class);
        //2，根据第一步获取到的用户信息，（用户信息中已经包含了角色/权限信息，直接取出来）
        //没有包含的话就从数据库中查询这个用户关联的角色/权限信息，基于角色的权限控制，查角色信息
        Set<String> roles = roleDao.getRolesByUid(user.getId());
        //通过关联的角色信息来进一步查询到关联的permission信息，返回set集合
        Set<String> permissions = permissionDao.getPermissionByUid(user.getId());
        System.out.println(permissions);
        Set<String> newPermissions = new HashSet<>();
        for (String per:permissions
        ) {
            if(per.startsWith("p:")){
                String p = per.replaceFirst("p:", "");
                newPermissions.add(p);
            }else{
                newPermissions.add(per);
            }
        }
        //3，把第二步中获取到的登录用户关联的角色set集合注入到AuthorizationInfo
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(newPermissions);
        return info;
    }


    /**
     * 登录认证
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
}

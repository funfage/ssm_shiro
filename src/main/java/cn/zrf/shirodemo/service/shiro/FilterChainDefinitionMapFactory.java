package cn.zrf.shirodemo.service.shiro;

import cn.zrf.shirodemo.dao.PermissionDao;
import cn.zrf.shirodemo.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;

public class FilterChainDefinitionMapFactory {

    @Autowired
    private PermissionDao permissionDao;

    private LinkedHashMap<String, String> permsMap = new LinkedHashMap<>();


    public LinkedHashMap<String, String> getfilterChainDefinitionMap(){
        //从数据库中获取数据，构造出一个map
        List<Permission> permissions = permissionDao.getAllPermissions();
        System.out.println(permissions);
        /**
         *  /login.html = anon
         *  /logout.html = logout  &lt;!&ndash;登出，默认跳转到根路径&ndash;&gt;
         *  /admin/userlist.html = perms[userlist] &lt;!&ndash;只有userlist权限才能访问&ndash;&gt;
         *  /admin/adduser.html = perms[adduser] &lt;!&ndash;只有adduser权限才能访问&ndash;&gt;
         *  /admin/** = authc     &lt;!&ndash; authc表示要登录后，才能访问 &ndash;&gt;
         *  /**= anon  &lt;!&ndash; anon表示匿名访问，就是不用登录
         */
        for (Permission perm: permissions
        ) {
            if(perm.getPname().startsWith("p:")){
                //删除前缀
                String p = perm.getPname().replaceFirst("p:", "");
                String p1 = perm.getPname().substring(2);
                System.out.println(p);
                System.out.println(p1);
                permsMap.put(perm.getUrl(), "perms[" + p + "]");
            }else{
                permsMap.put(perm.getUrl(), perm.getPname());
            }
        }
        System.out.println(permsMap);
        return permsMap;
    }


}

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
        //�����ݿ��л�ȡ���ݣ������һ��map
        List<Permission> permissions = permissionDao.getAllPermissions();
        System.out.println(permissions);
        /**
         *  /login.html = anon
         *  /logout.html = logout  &lt;!&ndash;�ǳ���Ĭ����ת����·��&ndash;&gt;
         *  /admin/userlist.html = perms[userlist] &lt;!&ndash;ֻ��userlistȨ�޲��ܷ���&ndash;&gt;
         *  /admin/adduser.html = perms[adduser] &lt;!&ndash;ֻ��adduserȨ�޲��ܷ���&ndash;&gt;
         *  /admin/** = authc     &lt;!&ndash; authc��ʾҪ��¼�󣬲��ܷ��� &ndash;&gt;
         *  /**= anon  &lt;!&ndash; anon��ʾ�������ʣ����ǲ��õ�¼
         */
        for (Permission perm: permissions
        ) {
            if(perm.getPname().startsWith("p:")){
                //ɾ��ǰ׺
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

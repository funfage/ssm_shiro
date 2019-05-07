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
 * �����Ҫ��Ȩ��֤����Ҫ�̳�AuthorizingRealm
 */
public class ShiroAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;


    /**
     * ��shiro��ר����������Ȩ��֤�ķ���
     * @param principals
     * ������Ȩ��֤�ķ��������Ĳ���PrincipalCollection principals���������ۣ�
     *         1�����������ǵ�¼�ɹ��Ժ���û���Ϣ��������¼��֤�ķ���doGetAuthenticationInfo�е�AuthenticationInfo�����һ�������������
     *         2�����ڵ�¼��֤�����Ƕ��realm�������Կ��ܴ�������û�����Ϣ���������������˵��һ�����ϣ������ж��Ԫ��
     *         3�������ļ�����ߵ�Ԫ�ص�˳����realm��spring.xml�����õ�˳��Ӱ��
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        /*System.out.println(principals);
        System.out.println(principals.getPrimaryPrincipal());
        System.out.println(principals.oneByType(User.class));
        System.out.println(principals.asList());*/
        //1�� �Ӳ���principals��ȡ��ǰ��¼�ɹ�����û���Ϣ
        User user = principals.oneByType(User.class);
        //2�����ݵ�һ����ȡ�����û���Ϣ�����û���Ϣ���Ѿ������˽�ɫ/Ȩ����Ϣ��ֱ��ȡ������
        //û�а����Ļ��ʹ����ݿ��в�ѯ����û������Ľ�ɫ/Ȩ����Ϣ�����ڽ�ɫ��Ȩ�޿��ƣ����ɫ��Ϣ
        Set<String> roles = roleDao.getRolesByUid(user.getId());
        //ͨ�������Ľ�ɫ��Ϣ����һ����ѯ��������permission��Ϣ������set����
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
        //3���ѵڶ����л�ȡ���ĵ�¼�û������Ľ�ɫset����ע�뵽AuthorizationInfo
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(newPermissions);
        return info;
    }


    /**
     * ��¼��֤
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1��token�Ѳ���ǿתΪUsernamePasswordToken
        UsernamePasswordToken token1 = (UsernamePasswordToken) token;
        //2����token1��ȡ���ύ�������û���
        String username = token1.getUsername();
        //3�������ݿ�������ѯ��û�û�����username���û���¼
        User user = null;
        try {
            user = userDao.getUserByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //4������user����ľ�������������׳�shiro������쳣��Ϣ
        if(user == null){
            throw new UnknownAccountException("�޴��û�");
        }
        if(user.getStatus() == 0){
            throw new LockedAccountException("�û��ѱ�����Ա����");
        }
        //5����һ������shiro���������ж��û�����д�������������Ƿ���ȷ
        //ʹ��username�䵱��ֵ
        ByteSource salt = ByteSource.Util.bytes(username);
        AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());
        //principals: �������û�����Ҳ�����ǵ�¼�û���user����
        //hashedCredentials: �����ݿ��л�ȡ���û�����
        //credentialsSalt:������ܵ���ֵ
        //RealName:ShiroRealm������
        return info;
    }
}

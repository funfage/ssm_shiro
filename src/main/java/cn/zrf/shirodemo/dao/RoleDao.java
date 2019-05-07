package cn.zrf.shirodemo.dao;

import cn.zrf.shirodemo.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface RoleDao {

    /**
     * ͨ���û�id����ѯ���û����������е�role�������rcode����
     */
    public Set<String> getRolesByUid(@Param("uid") int uid);

    public Set<Role> getRolesObjByUid(@Param("uid") int uid);
}

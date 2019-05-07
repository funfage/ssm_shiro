package cn.zrf.shirodemo.dao;

import cn.zrf.shirodemo.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface RoleDao {

    /**
     * 通过用户id，查询该用户关联的所有的role对象里的rcode集合
     */
    public Set<String> getRolesByUid(@Param("uid") int uid);

    public Set<Role> getRolesObjByUid(@Param("uid") int uid);
}

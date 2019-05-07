package cn.zrf.shirodemo.dao;

import cn.zrf.shirodemo.model.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface PermissionDao {

    /**
     * 通过Uid获取与用户关联的权限的set集合
     */
    Set<String> getPermissionByUid(@Param("uid") int uid);

    List<Permission> getAllPermissions();
}

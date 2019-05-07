package cn.zrf.shirodemo.dao;

import cn.zrf.shirodemo.model.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface PermissionDao {

    /**
     * ͨ��Uid��ȡ���û�������Ȩ�޵�set����
     */
    Set<String> getPermissionByUid(@Param("uid") int uid);

    List<Permission> getAllPermissions();
}

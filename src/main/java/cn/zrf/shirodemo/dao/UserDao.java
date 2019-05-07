package cn.zrf.shirodemo.dao;

import cn.zrf.shirodemo.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {

    public User getUserByUsername(@Param("username") String username);

}

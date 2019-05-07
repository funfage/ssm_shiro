package cn.zrf.shirodemo.service;

import cn.zrf.shirodemo.dao.UserDao;
import cn.zrf.shirodemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }
}

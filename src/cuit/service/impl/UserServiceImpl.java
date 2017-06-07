package cuit.service.impl;

import cuit.dao.UserDao;
import cuit.model.UserBean;
import cuit.service.UserService;

import java.io.IOException;

/**
 * Created by Esong on 2017/4/15.
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public int registerUser(UserBean userBean) {
        return userDao.registerUser(userBean);
    }

    @Override
    public int loginUser(String email, String password) {
        return userDao.loginUser(email,password);
    }

    @Override
    public int editUserPassword(int id, String oldPassword, String newPassword) {
        return userDao.editUserPassword(id,oldPassword,newPassword);
    }

    @Override
    public UserBean getUserDetail(int id) {
        return userDao.getUserDetail(id);
    }

    @Override
    public int editUserDetail(UserBean userBean) {
        return userDao.editUserDetail(userBean);
    }
}

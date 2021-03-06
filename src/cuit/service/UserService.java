package cuit.service;

import cuit.model.UserBean;

import java.io.IOException;

/**
 * Created by Esong on 2017/4/15.
 */
public interface UserService {
    int registerUser(UserBean userBean);
    int loginUser(String email, String password);
    int editUserPassword(int id, String oldPassword, String newPassword);
    UserBean getUserDetail(int id);
    int editUserDetail(UserBean userBean);
}

package com.chhai.dataanalyticrestfulapi.service;

import com.chhai.dataanalyticrestfulapi.model.User;
import com.chhai.dataanalyticrestfulapi.model.UserAccount;

import java.util.List;

public interface UserService {
    List<User> allUsers();

    List<User> findUserByName();

    User findUserByID(int id);

    int createNewUser(User user);

    int updateUser(User user, int id);

    int removeUser(int id);

    List<UserAccount> getAllUserAccounts();

}

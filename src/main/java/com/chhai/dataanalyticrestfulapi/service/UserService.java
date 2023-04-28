package com.chhai.dataanalyticrestfulapi.service;

import com.chhai.dataanalyticrestfulapi.model.User;
import com.chhai.dataanalyticrestfulapi.model.UserAccount;
import com.chhai.dataanalyticrestfulapi.model.request.UserRequest;

import java.util.List;

public interface UserService {
    List<User> allUsers();

    List<User> findUserByName();

    User findUserByID(int id);

    int createNewUser(UserRequest user);

    int updateUser(User user, int id);

    int removeUser(int id);

    List<UserAccount> getAllUserAccounts();

}

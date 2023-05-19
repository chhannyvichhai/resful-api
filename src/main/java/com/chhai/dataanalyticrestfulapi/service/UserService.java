package com.chhai.dataanalyticrestfulapi.service;

import com.chhai.dataanalyticrestfulapi.model.User;
import com.chhai.dataanalyticrestfulapi.model.UserAccount;
import com.chhai.dataanalyticrestfulapi.model.request.UserRequest;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {
    PageInfo<User> allUsers(int page, int size, String filterName);

    List<User> findUserByName(String username);

    User findUserByID(int id);

    int createNewUser(UserRequest user);

    int updateUser(User user, int id);

    int removeUser(int id);

    List<UserAccount> getAllUserAccounts();

}

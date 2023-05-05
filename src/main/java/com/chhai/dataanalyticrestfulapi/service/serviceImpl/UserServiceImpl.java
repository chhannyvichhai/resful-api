package com.chhai.dataanalyticrestfulapi.service.serviceImpl;

import com.chhai.dataanalyticrestfulapi.model.User;
import com.chhai.dataanalyticrestfulapi.model.UserAccount;
import com.chhai.dataanalyticrestfulapi.model.request.UserRequest;
import com.chhai.dataanalyticrestfulapi.repository.UserRepository;
import com.chhai.dataanalyticrestfulapi.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    // inject repository
    UserRepository userRepository;

    @Override
    public List<UserAccount> getAllUserAccounts() {
        return userRepository.getAllUserAccounts();
    }

    UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public PageInfo<User> allUsers(int page, int size, String filterName) {

        // PageHelper is here
        PageHelper.startPage(page, size);
        return new PageInfo<>(userRepository.allUsers(filterName));
//        return userRepository.allUsers();
    }

    @Override
    public List<User> findUserByName() {
        return null;
    }

    @Override
    public User findUserByID(int id) {
        return userRepository.findUserByID(id);
    }

    @Override
    public int createNewUser(UserRequest user) {
        return userRepository.createNewUser(user);
    }

    @Override
    public int updateUser(User user, int id) {
        return userRepository.updateUsers(user, id);
    }

    @Override
    public int removeUser(int id) {
        return userRepository.removeUser(id);
    }
}

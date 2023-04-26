package com.chhai.dataanalyticrestfulapi.repository;

import com.chhai.dataanalyticrestfulapi.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRepository {
    // if you want to change id to user id
    @Result(column = "id", property = "userId")
    @Select("select * from users_tb")
    List<User> allUsers();
    List<User> findUserByName(String username);
    int createNewUser(User user);
    int updateUserByID(int id);
    int removeUser(int id);


    @Select("select * from users_tb where id = #{id}")
    @Result(property = "userId", column = "id")
    User findUserByID(int id);


}

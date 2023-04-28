package com.chhai.dataanalyticrestfulapi.repository;

import com.chhai.dataanalyticrestfulapi.model.Account;
import com.chhai.dataanalyticrestfulapi.model.User;
import com.chhai.dataanalyticrestfulapi.model.UserAccount;
import com.chhai.dataanalyticrestfulapi.model.request.UserRequest;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository
public interface UserRepository {
    // if you want to change id to user id
    @Result(column = "id", property = "userId")
    @Select("select * from users_tb")
    List<User> allUsers();
    List<User> findUserByName(String username);

    @Select("insert into users_tb (username, gender, address) values (#{user.username}, #{user.gender},#{user.address}) returning id")
    int createNewUser(@Param("user") UserRequest user);
    int updateUserByID(int id);
//    int removeUser(int id);

    @Result(property = "userId", column = "id")
    @Select("select * from users_tb where id = #{id}")
    User findUserByID(int id);

    @Results({
           @Result(column = "id", property = "userId"),
            @Result(column = "id", property = "accounts", many = @Many(select = "findAccountByUserId"))

    })
    @Select("select * from users_tb")
    List<UserAccount> getAllUserAccounts();

    @Results({
            @Result(property = "accountName", column = "account_name"),
            @Result(property = "accountNumber", column = "account_no"),
            @Result(property = "transferLimit", column = "transfer_limit"),
            @Result(column = "account_type", property = "accountType", one = @One(select = "com.chhai.dataanalyticrestfulapi.repository.AccountRepository.getAccountTypeByID")),
    })

    @Select("select * from useraccount_tb inner join account_tb a on a.id = useraccount_tb.account_id\n" +
            "where user_id = #{id}")
    List<Account> findAccountByUserId(int id);

    @Update("update users_tb\n" +
            "set username = #{user.username}, gender = #{user.gender}, address = #{user.address}\n" +
            "where id = #{id}")
    int updateUsers(@PathVariable("user") User user, int id);

    @Delete("delete from users_tb\n" +
            "where id = #{id}")
    int removeUser(int id);





}

package com.chhai.dataanalyticrestfulapi.repository;

import com.chhai.dataanalyticrestfulapi.model.Account;
import com.chhai.dataanalyticrestfulapi.model.User;
import com.chhai.dataanalyticrestfulapi.model.UserAccount;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.AnnotationConfigUtils;
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

    @Insert("insert into users_tb (username, gender, address) values (#{user.username}, #{user.gender},#{user.address})")
    int createNewUser(@Param("user") User user);
    int updateUserByID(int id);
    int removeUser(int id);

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


}
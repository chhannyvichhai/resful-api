package com.chhai.dataanalyticrestfulapi.service;

import com.chhai.dataanalyticrestfulapi.model.Account;

import java.util.List;

public interface AccountService {

    List<Account> getAllAccount();
    int createAccount(Account account);
    int updateAccount(Account account);
    Account findAccountByID(int id);
}

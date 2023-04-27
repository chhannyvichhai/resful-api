package com.chhai.dataanalyticrestfulapi.mapper;

import com.chhai.dataanalyticrestfulapi.controller.AccountController;
import com.chhai.dataanalyticrestfulapi.model.Account;
import com.chhai.dataanalyticrestfulapi.model.response.AccountResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AutoAccountMapper {

    // account -> accountResponse
    List<AccountResponse> mapToAccountResponse(List<Account> accounts);
    // account - response -> account
    List<Account> mapToAccount(List<AccountResponse> responses);
}

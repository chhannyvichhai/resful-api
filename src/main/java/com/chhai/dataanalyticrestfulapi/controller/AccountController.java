package com.chhai.dataanalyticrestfulapi.controller;

import com.chhai.dataanalyticrestfulapi.mapper.AutoAccountMapper;
import com.chhai.dataanalyticrestfulapi.model.Account;
import com.chhai.dataanalyticrestfulapi.model.response.AccountResponse;
import com.chhai.dataanalyticrestfulapi.service.AccountService;
import com.chhai.dataanalyticrestfulapi.utils.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController
@RequestMapping("/account")

public class AccountController {
    // inject account service
    final private AccountService accountService;
    final private AutoAccountMapper autoAccountMapper;
    AccountController(AccountService accountService , AutoAccountMapper autoAccountMapper){
        this.accountService = accountService;
        this.autoAccountMapper = autoAccountMapper;
    }

    @GetMapping("/allaccounts")
    public Response<List<AccountResponse>> getAllAccount() {

        try {
            List<Account> allAccount = accountService.getAllAccount();

            List<AccountResponse> accountResponses = autoAccountMapper.mapToAccountResponse(allAccount);


            return Response.<List<AccountResponse>>ok().setPayload(accountResponses).setMessage("Successfully retrieved all account information ");

        }catch (Exception ex){
            System.out.println("Something wrong: "+ex.getMessage());
            return Response.<List<AccountResponse>>exception().setMessage("Exception occurs! Failed to retrieved account information");
        }
    }

}

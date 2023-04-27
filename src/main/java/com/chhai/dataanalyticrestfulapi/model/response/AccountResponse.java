package com.chhai.dataanalyticrestfulapi.model.response;

import com.chhai.dataanalyticrestfulapi.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private int id;
    private String accountName;
    private String accountNumber;
    private String profile;
    private String phoneNumber;
    private int transferLimit;
    private AccountType accountType;
}

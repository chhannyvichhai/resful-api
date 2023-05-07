package com.chhai.dataanalyticrestfulapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    private int transactionId;
    private int senderAccount;
    private int receiverAccount;
    private float amount;
    private String remark;
    private LocalDate transferAt;
}

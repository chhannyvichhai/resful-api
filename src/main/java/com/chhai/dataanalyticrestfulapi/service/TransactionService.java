package com.chhai.dataanalyticrestfulapi.service;

import com.chhai.dataanalyticrestfulapi.model.Transaction;
import com.github.pagehelper.PageInfo;

public interface TransactionService {
    PageInfo<Transaction> allTransactions(int page, int size, int filterId);
    int createNewTransaction(Transaction transaction);
    int updateTransaction(Transaction transaction, int transactionId);
    int deleteTransaction(int transactionId);
}

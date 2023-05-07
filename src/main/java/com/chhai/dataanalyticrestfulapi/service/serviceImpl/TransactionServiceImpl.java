package com.chhai.dataanalyticrestfulapi.service.serviceImpl;

import com.chhai.dataanalyticrestfulapi.model.Transaction;
import com.chhai.dataanalyticrestfulapi.repository.TransactionRepository;
import com.chhai.dataanalyticrestfulapi.service.TransactionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    TransactionRepository transactionRepository;
    TransactionServiceImpl(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @Override
    public PageInfo<Transaction> allTransactions(int page, int size, int filterId) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(transactionRepository.allTransactions(filterId));
    }

    @Override
    public int createNewTransaction(Transaction transaction) {
        return transactionRepository.createNewTransaction(transaction);
    }

    @Override
    public int updateTransaction(Transaction transaction, int transactionId) {
        return transactionRepository.updateTransaction(transaction,transactionId);
    }

    @Override
    public int deleteTransaction(int transactionId) {
        return transactionRepository.deleteTransaction(transactionId);
    }
}

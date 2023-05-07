package com.chhai.dataanalyticrestfulapi.repository;

import com.chhai.dataanalyticrestfulapi.model.Transaction;
import com.chhai.dataanalyticrestfulapi.repository.provider.TransactionProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TransactionRepository {
    @Results({
            @Result(column = "id", property = "transactionId"),
            @Result(column = "sender_account_id", property = "senderAccount"),
            @Result(column = "receiver_account_id", property = "receiverAccount"),
            @Result(column = "transfer_at", property = "transferAt")
    })
    @SelectProvider(type = TransactionProvider.class, method = "getAllTransactions")
    List<Transaction> allTransactions(int filterId);

    @InsertProvider(type = TransactionProvider.class, method = "insertTransaction")
    @Options(useGeneratedKeys = true, keyProperty = "transactionId")
    int createNewTransaction(Transaction transaction);

    @UpdateProvider(type = TransactionProvider.class, method = "updateTransaction")
    int updateTransaction(Transaction transaction, int transactionId);

    @DeleteProvider(type = TransactionProvider.class, method = "deleteTransaction")
    int deleteTransaction(int transactionId);
}

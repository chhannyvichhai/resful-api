package com.chhai.dataanalyticrestfulapi.repository.provider;

import com.chhai.dataanalyticrestfulapi.model.Transaction;
import org.apache.ibatis.jdbc.SQL;

public class TransactionProvider {
    public static String getAllTransactions(int filterId){
        return new SQL(){{
            SELECT("*");
            FROM("transaction_tb");
            if (filterId != 0){
                WHERE("id=#{filterId}");
            }
        }}.toString();
    }

    public static String insertTransaction(Transaction transaction){
        return new SQL(){{
            INSERT_INTO("transaction_tb");
            VALUES("sender_account_id", "#{senderAccount}");
            VALUES("receiver_account_id", "#{receiverAccount}");
            VALUES("amount", "#{amount}");
            VALUES("remark", "#{remark}");
            VALUES("transfer_at", "#{transferAt}");
        }}.toString();
    }

    public static String updateTransaction(Transaction transaction, int transactionId) {
        return new SQL() {{
            UPDATE("transaction_tb");
            SET("sender_account_id = #{transaction.senderAccount}");
            SET("receiver_account_id = #{transaction.receiverAccount}");
            SET("transfer_at = #{transaction.transferAt}");
            SET("amount = #{transaction.amount}");
            SET("remark = #{transaction.remark}");
            WHERE("id = #{transactionId}");
        }}.toString();
    }

    public static String deleteTransaction(int transactionId){
        return new SQL(){{
            DELETE_FROM("transaction_tb");
            WHERE("id = #{transactionId}");
        }}.toString();
    }
}

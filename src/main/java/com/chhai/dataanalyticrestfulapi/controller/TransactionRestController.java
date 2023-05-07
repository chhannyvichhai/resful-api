package com.chhai.dataanalyticrestfulapi.controller;

import com.chhai.dataanalyticrestfulapi.model.Transaction;
import com.chhai.dataanalyticrestfulapi.service.TransactionService;
import com.chhai.dataanalyticrestfulapi.utils.Response;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionRestController {
    private final TransactionService transactionService;

    TransactionRestController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping("/alltransaction")
    public Response<PageInfo<Transaction>> getAllTransaction(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") int filterId){
        try {
            PageInfo<Transaction> transaction = transactionService.allTransactions(page, size, filterId);
            return Response.<PageInfo<Transaction>>ok().setPayload(transaction).setMessage("Successfully retrieved all transaction!");
        }catch (Exception ex){
            ex.printStackTrace();
            return Response.<PageInfo<Transaction>>exception().setMessage("Fail to retrieved the transaction! Exception occurred");
        }
    }

    @PostMapping("/newtransaction")
    public Response<Transaction> createTransaction(@Valid @RequestBody Transaction transaction){
        try {
            int rowAffected = transactionService.createNewTransaction(transaction);
            if (rowAffected>0){
                return Response.<Transaction>createSuccess().setMessage("Created transaction Successfully!").setPayload(transaction);
            } else {
                return Response.<Transaction>badRequest().setMessage("Fail to create new transaction!").setSuccess(false);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return Response.<Transaction>exception().setSuccess(false).setMessage("Fail to create new transaction!");
        }
    }

    @PutMapping("/{id}")
    public Response<Transaction> updateTransaction(@RequestBody Transaction transaction, @PathVariable int id){
        try {
            transaction.setTransactionId(id);
            int affectedRow = transactionService.updateTransaction(transaction, id);
            if(affectedRow>0){
                return Response.<Transaction>updateSuccess().setPayload(transaction).setMessage("Update Successfully!");
            }else {
                return Response.<Transaction>notFound().setMessage("Cannot find a transaction to update!").setSuccess(false);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return Response.<Transaction>exception().setMessage("Fail to update a transaction");
        }
    }

    @DeleteMapping("/{id}")
    public Response<Transaction>deleteTransaction(@PathVariable int id){
        int affectedRow = transactionService.deleteTransaction(id);
        try {
            if(affectedRow>0){
                return Response.<Transaction>deleteSuccess().setMessage("Successfully deleted a transaction");
            } else {
                return Response.<Transaction>notFound().setMessage("Can't find a transaction").setSuccess(false);
            }
        } catch (Exception exception){
            return Response.<Transaction>exception().setMessage("Fail to delete a transaction").setSuccess(false);
        }
    }
}

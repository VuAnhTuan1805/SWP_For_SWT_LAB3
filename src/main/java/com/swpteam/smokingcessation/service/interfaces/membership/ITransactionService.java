package com.swpteam.smokingcessation.service.interfaces.membership;

import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.entity.Transaction;

public interface ITransactionService {

    Transaction createTransaction(Account account, double amount);

    void makeAsPaid(String transactionId);
}

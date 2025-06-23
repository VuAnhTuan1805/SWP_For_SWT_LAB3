package com.swpteam.smokingcessation.service.impl.membership;

import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.entity.Transaction;
import com.swpteam.smokingcessation.repository.TransactionRepository;
import com.swpteam.smokingcessation.domain.enums.TransactionStatus;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.service.interfaces.identity.IAccountService;
import com.swpteam.smokingcessation.service.interfaces.membership.ITransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionServiceImpl implements ITransactionService {

    TransactionRepository transactionRepository;
    IAccountService accountService;

    @Override
    @Transactional
    @CachePut(value = "TRANSACTION_CACHE", key = "#result.getId()")
    public Transaction createTransaction(Account account, double amount) {
        accountService.findAccountById(account.getId());

        Transaction transaction = Transaction.startTransaction(account);
        transaction.setAmount(amount);

        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void makeAsPaid(String transactionId) {
        Transaction transaction = findTransactionById(transactionId);

        transaction.setStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);
    }

    @Cacheable(value = "TRANSACTION_CACHE", key = "#id")
    private Transaction findTransactionById(String id) {
        Transaction transaction = transactionRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        if (transaction.getAccount().isDeleted()) {
            throw new AppException(ErrorCode.ACCOUNT_DELETED);
        }

        return transaction;
    }
}

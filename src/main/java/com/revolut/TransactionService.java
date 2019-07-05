package com.revolut;

import com.revolut.dao.Account;
import com.revolut.dao.Transaction;

import java.util.List;

/**
 * @author ppai
 */
public interface TransactionService {

    void makeTransaction(Transaction transaction);

    void addUser(Account account);

    Account getUser(String accountID);

    List<Account> getAllusers();
}

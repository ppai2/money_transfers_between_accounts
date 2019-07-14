package com.revolut;

import com.revolut.dao.Account;
import com.revolut.dao.Transaction;

import java.util.List;

/**
 * Transaction service interface
 *
 * @author ppai
 */
public interface TransactionService {

    boolean makeTransaction(Transaction transaction);

    boolean addUser(Account account);

    boolean updateUserAccount(Account account);

    List<Account> getAllusers();

    boolean deleteAccount(String accountID);
}

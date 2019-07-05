package com.revolut;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.revolut.dao.Account;
import com.revolut.dao.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author ppai
 */
public class TransactionServiceImpl implements TransactionService {
    final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    final Map<String, Account> accountMap;

    public TransactionServiceImpl() {
        accountMap = Maps.newHashMap();
    }

    @Override
    public void makeTransaction(Transaction transaction) {
        Account senderAccount = accountMap.get(transaction.getSenderAccountID());
        Account recipientAccount = accountMap.get(transaction.getRecipientAccountID());
        Double amount = transaction.getAmount();

        if (senderAccount.contains(amount)) {
            // sender
            Double sendersBalanceBeforeTransaction = senderAccount.getBalance().doubleValue();
            Double sendersUpdatedBalanceAfterTransaction = sendersBalanceBeforeTransaction-amount;
            senderAccount.setBalance(sendersUpdatedBalanceAfterTransaction);
            accountMap.put(senderAccount.getAccountID(), senderAccount);

            // recipient
            Double recipientsBalanceBeforeTransaction = recipientAccount.getBalance();
            Double recipientsUpdatedBalanceAfterTransaction = recipientsBalanceBeforeTransaction+amount;
            recipientAccount.setBalance(recipientsUpdatedBalanceAfterTransaction);
            accountMap.put(recipientAccount.getAccountID(), recipientAccount);
            logger.info("Amount {} transferred from sender {} to recipient {}",
                    amount, senderAccount.getAccountID(), recipientAccount.getAccountID());
            logger.info("Sender account ID {} balance update : {}", senderAccount.getAccountID(), senderAccount.getBalance());
            logger.info("Recipient account ID {} balance update : {}", recipientAccount.getAccountID(), recipientAccount.getBalance());
        } else {
            logger.error("User account ID {} does not have sufficient balance", senderAccount.getAccountID());
        }

    }

    @Override
    public void addUser(Account account) {
        if (accountMap.containsKey(account.getAccountID())) {
            logger.error("Account with ID {} already exists. Please use a new ID", account.getAccountID());
        } else {
            accountMap.put(account.getAccountID(), account);
            logger.info("Account {} has been inserted in the account map", account.getAccountID());
        }
    }

    @Override
    public Account getUser(String accountID) {
        return accountMap.get(accountID);
    }

    @Override
    public List<Account> getAllusers() {
        List<Account> allUsers = Lists.newArrayList();
        for (Map.Entry entry : accountMap.entrySet()) {
            allUsers.add((Account) entry.getValue());
        }
        logger.info("All users from the account map", allUsers);
        return allUsers;
    }
}

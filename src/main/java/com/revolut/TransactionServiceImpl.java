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
 * Implementation of Transaction Service
 *
 * @author ppai
 */
public class TransactionServiceImpl implements TransactionService {
    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    final Map<String, Account> accountMap;

    /**
     * For the transaction service implementation, I have used a local cache populated with
     * some test user accounts. But there are a lot of other options for in memory datastore like H2 database, SQLite etc.
     * For these databases, we need to write a SQL script which would populate the database with some test accounts and
     * then we can make any transaction or tasks.
     *
     * In this case, I have used a HashMap for storing the user accounts and their account ID with the account ID
     * being the key and account object as a value.
     */
    public TransactionServiceImpl() {
        accountMap = Maps.newHashMap();
        createTestAccountMapForUnitTesting();
    }

    /**
     * this method makes transaction between sender and recipient accounts
     *
     * @param transaction
     * @return boolean status if the transaction is true (SUCCESS) or false (FAIL)
     * @throws NullPointerException
     */
    @Override
    public boolean makeTransaction(Transaction transaction) throws NullPointerException {
        Double amount = transaction.getAmount();

        if (transaction.getAmount() < 0) {
            return false;
        }

        Account senderAccount = accountMap.get(transaction.getSenderAccountID());
        Account recipientAccount = accountMap.get(transaction.getRecipientAccountID());
        Double sendersBalance = senderAccount.getBalance();
        Double recipientsBalance = recipientAccount.getBalance();

        if(!(accountMap.containsKey(senderAccount.getAccountID()) ) || !(accountMap.containsKey(recipientAccount.getAccountID()))) {
            return false;
        }

        if (sendersBalance >= amount) {
            // sender
            sendersBalance -= amount;
            senderAccount.setBalance(sendersBalance);
            senderAccount.setAmount(sendersBalance);
            accountMap.put(senderAccount.getAccountID(), senderAccount);

            // recipient
            recipientsBalance += amount;
            recipientAccount.setBalance(recipientsBalance);
            recipientAccount.setAmount(recipientsBalance);
            accountMap.put(recipientAccount.getAccountID(), recipientAccount);
            
            return true;
        }
        return false;
    }

    /**
     * Adds a new user account
     *
     * @param account
     * @return boolean status if the transaction is true (SUCCESS) or false (FAIL)
     */
    @Override
    public boolean addUser(Account account) {
        if ((account.getAccountID() == null) || (account.getAccountID() == "")) {
            return false;
        }

        accountMap.put(account.getAccountID(), account);
        logger.info("Account {} has been inserted", account.getAccountID());

        logger.info("account map : {}", accountMap);
        return true;
    }

    /**
     * Updates the user account's balance in case of a new deposit
     *
     * @param account
     */
    public boolean updateUserAccount(Account account) {
        Account userAccount = accountMap.get(account.getAccountID());
        Double newAmount = account.getAmount();
        if (newAmount >= 0) {
            Double balance = userAccount.getBalance();
            balance+=newAmount ;
            userAccount.setBalance(balance);
            userAccount.setAmount(userAccount.getBalance());
            accountMap.put(account.getAccountID(), userAccount);
            return true;
        }
        return false;
    }

    /**
     * Retrieves all the users from the cache
     *
     * @return List of all user accounts
     */
    @Override
    public List<Account> getAllusers() {
        List<Account> allUsers = Lists.newArrayList();
        for (Map.Entry entry : accountMap.entrySet()) {
            allUsers.add((Account) entry.getValue());
        }
        logger.info("All users from the account map", allUsers);
        return allUsers;
    }

    /**
     * Removes a user account from the cache
     *
     * @param accountID
     * @return boolean status if the transaction is true (SUCCESS) or false (FAIL)
     */
    @Override
    public boolean deleteAccount(String accountID) {
        if (accountMap.containsKey(accountID)) {
            accountMap.remove(accountID);
            return true;
        }
        return false;
    }

    /**
     * test account map for unit testing with some user accounts
     */
    public void createTestAccountMapForUnitTesting() {
        Account account1 = new Account("1", 100.00, 100.00);
        accountMap.put("1", account1);
        Account account2 = new Account("2", 10.00, 10.00);
        accountMap.put("2", account2);
        Account account3 = new Account("3", 250.00, 250.00);
        accountMap.put("3", account3);
    }

    public Map<String, Account> getAccountMap() {
        return accountMap;
    }
}

package com.revolut.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ppai
 */
public class Account {

    Logger logger = LoggerFactory.getLogger(Account.class);

    private String accountID;
    private Double balance;
    private Double amount;

    public Account(String accountID, String userName, Double amount) {
        this.accountID = accountID;
        this.amount = amount;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public boolean contains(Double amount) {
        if (amount < balance)  {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return new StringBuffer().append(
                getAccountID() + "," +
                        getAmount() + "," +
                        getBalance()).toString();
    }
}

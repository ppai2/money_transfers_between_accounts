package com.revolut.dao;

/**
 * Account class for account details
 *
 * @author ppai
 */
public class Account {

    private String accountID;
    private Double balance;
    private Double amount;

    public Account(String accountID, Double balance, Double amount) {
        this.balance = balance;
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

    @Override
    public String toString() {
        return "Account{" +
                "accountID='" + accountID + '\'' +
                ", balance=" + balance +
                ", amount=" + amount +
                '}';
    }
}

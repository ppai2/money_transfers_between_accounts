package com.revolut.dao;

/**
 * @author ppai
 */
public class Transaction {

    private String senderAccountID;
    private String recipientAccountID;
    private Double amount;

    public Transaction(String senderAccountID, String recipientAccountID, Double amount) {
        this.senderAccountID = senderAccountID;
        this.recipientAccountID = recipientAccountID;
        this.amount = amount;
    }

    public String getSenderAccountID() {
        return senderAccountID;
    }

    public void setSenderAccountID(String senderAccountID) {
        this.senderAccountID = senderAccountID;
    }

    public String getRecipientAccountID() {
        return recipientAccountID;
    }

    public void setRecipientAccountID(String recipientAccountID) {
        this.recipientAccountID = recipientAccountID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return new StringBuffer().append(
                getSenderAccountID() + "," +
                getRecipientAccountID() + "," +
                amount).toString();
    }
}

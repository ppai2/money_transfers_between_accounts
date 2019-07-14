package com.revolut;

import com.revolut.dao.Account;
import com.revolut.dao.Transaction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test class for transaction service
 */
public class TransactionServiceImplTest {

	private TransactionServiceImpl transactionService = null;
	private Double expectedBalance = 0.0;
	private Double actualBalance = 0.0;

	@Before
	public void setUp() {
		transactionService = new TransactionServiceImpl();
		transactionService.createTestAccountMapForUnitTesting();
	}

	@Test
	public void testMakeTransactionReturnsTrueWhenTransactionIsValid() {
		final String senderAccountID = "1";
		final String recipientAccountID = "2";
		final Double amountToBeTransferred = 50.00;
		Double expectedSendersBalance = 0.0;
		Double expectedRecipientsBalance = 0.0;

		final Transaction transaction = new Transaction(senderAccountID, recipientAccountID, amountToBeTransferred);

		final boolean status = transactionService.makeTransaction(transaction);
		if (status) {
			expectedSendersBalance = transactionService.getAccountMap().get(senderAccountID).getBalance();
			expectedRecipientsBalance = transactionService.getAccountMap().get(recipientAccountID).getBalance();
		}

		assertTrue("status of the transaction is expected to be true", status);
		assertEquals("senders actual balance is expected to match the expected balance", String.valueOf(50.0), String.valueOf(expectedSendersBalance));
		assertEquals("recipients actual balance is expected to match the expected balance", String.valueOf(60.0), String.valueOf(expectedRecipientsBalance));
	}

	@Test
	public void testMakeTransactionReturnsFalseWhenSenderDoesNotHaveEnoughBalanceToTransfer() {
		final String senderAccountID = "2";
		final String recipientAccountID = "3";
		final Double amountToBeTransferred = 150.00;
		final Transaction transaction = new Transaction(senderAccountID, recipientAccountID, amountToBeTransferred);

		final boolean status = transactionService.makeTransaction(transaction);

		assertFalse("status of the transaction is expected to be false", status);
	}

	@Test
	public void testMakeTransactionReturnsFalseWhenAmountIsNegative() {
		final String senderAccountID = "2";
		final String recipientAccountID = "3";
		final Double amountToBeTransferred = -1.0;
		final Transaction transaction = new Transaction(senderAccountID, recipientAccountID, amountToBeTransferred);

		final boolean status = transactionService.makeTransaction(transaction);

		assertFalse("status of the transaction is expected to be false", status);
	}

	@Test (expected = NullPointerException.class)
	public void testMakeTransactionThrowsExceptionAndReturnsFalseWhenAccountIsInvalid() {
		final String senderAccountID = "-2";
		final String recipientAccountID = "3";
		final Double amountToBeTransferred = 1.0;
		final Transaction transaction = new Transaction(senderAccountID, recipientAccountID, amountToBeTransferred);

		transactionService.makeTransaction(transaction);
	}

	@Test
	public void testUpdateUserAccount() {
		final Account testAccount = new Account("2", 500.00, 500.00);
		expectedBalance = transactionService.getAccountMap().get(testAccount.getAccountID()).getBalance()+testAccount.getBalance();

		final boolean status = transactionService.updateUserAccount(testAccount);
		if (status) {
			actualBalance = transactionService.getAccountMap().get(testAccount.getAccountID()).getBalance();
		}

		assertEquals("Actual balance is expected to match the expected balance", expectedBalance, actualBalance);
	}
}

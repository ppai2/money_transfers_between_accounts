package com.revolut.http;

import com.google.gson.Gson;
import com.revolut.TransactionService;
import com.revolut.dao.Account;
import com.revolut.dao.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

/**
 * Controller class for transaction service
 * HTTP methods implemented:
 * POST for making a transaction and adding new user account
 * PUT for depositing money in the user account
 * GET for retrieving all the user accounts from the cache
 * DELETE for removing a user account
 */
public class TransactionController {

	private final Logger logger = LoggerFactory.getLogger(TransactionController.class);
	private TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	public void controllers() {

		post( "/transaction", (req, res)-> {
			res.type("application/json");
			Transaction transaction = new Gson().fromJson(req.body(), Transaction.class);
			boolean transactionStatus = transactionService.makeTransaction(transaction);

			if(!transactionStatus) {
				return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, new Gson().toJson("Sender does not have enough balance")));
			}
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
		});

		post( "/addAccount", (req, res)-> {
			res.type("application/json");
			Account account = new Gson().fromJson(req.body(), Account.class);
			logger.info("account ID {} and amount {}, balance {}", account.getAccountID(), account.getAmount(), account.getBalance());
			boolean addUserStatus = transactionService.addUser(account);

			if(!addUserStatus) {
				notFound((request, response) -> {
					response.type("application/json");
					response.status(400);
					response.body("Bad Request");
					return response;
				});
			}
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
		});

		put("/depositMoney/:accountID", (req, res)-> {
			res.type("application/json");
			Account account = new Gson().fromJson(req.body(), Account.class);
			boolean status = transactionService.updateUserAccount(account);

			if (!status) {
				return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, new Gson().toJson("Unable to deposit amount in the account!")));
			}
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
		});

		get( "/allUsers", (req, res)-> {
			res.type("application/json");
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(transactionService.getAllusers())));
		});

		delete( "/deleteAccount/:accountID", (req, res)-> {
			res.type("application/json");
			boolean deleteStatus = transactionService.deleteAccount(req.params(":accountID"));
			if(!deleteStatus) {
				return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, new Gson().toJson("Account ID is unavailable in the map")));
			}
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
		});
	}
}

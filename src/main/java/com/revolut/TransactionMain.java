package com.revolut;

import com.google.gson.Gson;
import com.revolut.dao.Account;
import com.revolut.dao.Transaction;
import com.revolut.http.StandardResponse;
import com.revolut.http.StatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * @author ppai
 */
public class TransactionMain {
    public static void main(String[] args) {

        final Logger logger = LoggerFactory.getLogger(TransactionMain.class);

        get("/hello", (req, res)->"Hello, world");

        get("/hello/:name", (req,res)->{
            return "Hello, "+ req.params(":name");
        });

        final TransactionService transactionService = new TransactionServiceImpl();

        post( "/transaction", (req, res)-> {
            res.type("application/json");
            Transaction transaction = new Gson().fromJson(req.body(), Transaction.class);
//            transaction.setSenderAccountID(req.queryParams(":sender"));
//            transaction.setRecipientAccountID(req.queryParams(":recipient"));
//            transaction.setAmount(Double.parseDouble(req.queryParams(":amount")));
            transactionService.makeTransaction(transaction);

            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        post( "/addAccount", (req, res)-> {
            res.type("application/json");
            Account account = new Gson().fromJson(req.body(), Account.class);
            logger.info("account ID {} and amount {}, balance {}", account.getAccountID(), account.getAmount(), account.getBalance());
            transactionService.addUser(account);

            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        get( "/allUsers", (req, res)-> {
            res.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(transactionService.getAllusers())));
        });
    }
}

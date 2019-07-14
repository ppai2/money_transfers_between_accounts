package com.revolut;

import com.revolut.http.TransactionController;

/**
 * Application main class
 *
 * @author ppai
 */
public class TransactionMain {

    public static void main(String[] args) {
        startService();
    }

    /**
     * the main service runs on localhost (127.0.0.1) and port (4567)
     * for any HTTP requests we make, we use this IP and port
     */
    public static void startService() {

        final TransactionService transactionService = new TransactionServiceImpl();
        final TransactionController transactionController = new TransactionController(transactionService);
        transactionController.controllers();
    }
}

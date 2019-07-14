# Amount transaction between user accounts 
spark java framework, jetty, google guice, google gson, REST APIs

Description: A data model and backend implementation for money transfers between user accounts. 
REST API implemementation using Java Spark framework and Google Guice dependency injection. 

Add new user account : 

Request URL :

	POST http://localhost:4567/addAccount

(This will insert user account #6 into the cache)

Request Body :
	
	
	{
		"accountID": 6,
		"balance": 10.00,
		"amount": 10.00
	}

Deposit money in the user account : 

Request URL : 

	PUT http://localhost:4567/depositMoney/1
(This will deposit $10.00 into user account #1)

Request Body :

	{
		"accountID": 1,
		"balance": 10.00,
		"amount": 10.00
	}

Make transaction : 

Request URL : 

	POST http://localhost:4567/transaction
(This will transfer $10.00 from sender account #1 to recipient account #2)

Request Body : 

	{
		"senderAccountID": 1,
		"recipientAccountID": 2,
		"amount": 10.00
	}

Get all users : 

Request URL : 

	GET http://localhost:4567/allUsers
(This will retrieve all the user accounts from the cache)

Delete a user account : 

Request URL : 

	DELETE http://localhost:4567/deleteAccount/2
(This will delete user account #2 from the cache)

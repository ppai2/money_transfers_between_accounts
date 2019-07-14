# Amount transaction between user accounts 
- [sparkjava](https://mvnrepository.com/artifact/com.sparkjava/spark-core/2.7.2)
- Jetty
- Google Guice
- Google GSON
- REST APIs

## Description: 
A data model and backend implementation for money transfers between user accounts. 


REST API implemementation using Java Spark framework and Google Guice dependency injection. 

Due to time constraints and for convenience, I have used HashMap as a local data cache for couple of user accounts. But for scalable implementation and efficiency in data storage and retrieval, we can use the following in-memory datastores - 
	
[H2 database](https://www.h2database.com/html/main.html)
	
[SQLite](https://www.sqlite.org/index.html)
	
For implementing this model with these datastores, we might need to write a script which loads the data in the datastores before we run any transaction related tasks like delete, get all users etc. 
	


### **Add new user account** : 

_Request URL :_

	POST http://localhost:4567/addAccount

(This following request body will insert user account #6 into the cache)

_Request Body :_
	
	
	{
		"accountID": 6,
		"balance": 10.00,
		"amount": 10.00
	}



### **Deposit money in the user account :** 

_Request URL :_ 

	PUT http://localhost:4567/depositMoney/1
(The following request body will deposit $10.00 into user account #1)

_Request Body :_

	{
		"accountID": 1,
		"balance": 10.00,
		"amount": 10.00
	}


### **Make transaction like sending money :** 

_Request URL :_ 

	POST http://localhost:4567/transaction
(This following request body will transfer $10.00 from sender account #1 to recipient account #2)

_Request Body :_ 

	{
		"senderAccountID": 1,
		"recipientAccountID": 2,
		"amount": 10.00
	}



### **Retrieve all users :** 

_Request URL :_ 

	GET http://localhost:4567/allUsers
(This will retrieve all the user accounts from the cache)



### **Delete a user account :** 

_Request URL :_ 

	DELETE http://localhost:4567/deleteAccount/2
(This will delete user account #2 from the cache)

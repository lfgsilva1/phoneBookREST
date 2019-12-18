# phoneBookREST
A simple phone book REST API

This application is a simple REST API to manage contacts. Each contact has just one address but can have various phone numbers. The technologies used are:
- JAX-RS2.0 
- JPA 2.1
- EJB3.2 
- Hibernate
- MySQL database 

The application server used was the wildfly-17.0.1.Final, and a docker container was used to the database. Also, the project was developed in Linux, more concretely on Debian 5.3.15-1 (2019-12-07) x86_64 GNU/Linux. 

#### To prepare the API setup

Step 1: Get the docker image of MySQL data server:

    #sudo docker pull mysql:8.0.18

Step 2: Create the container:

    #sudo docker run --name mysql-server -e MYSQL_ROOT_PASSWORD=test -p3306:3306 -d container_id

Step 3: Access the container:

    #sudo docker exec -it container_id /bin/bash

Step 4: Login into MySQL and create the user test:

    #mysql -U mysql -p

Step 5: Grant privileges to the user to be used with the API:

    #GRANT ALL PRIVILEGES ON *.* TO 'test'@'172.17.0.1' WITH GRANT OPTION; 

    #FLUSH PRIVILEGES;

Step 6: Create the database to be used with API:

    #create database PhoneBook;

Step 7: Then we can start the Wildfly and install the database connector to MySQL on it testing the connection.

#### Start and use the API

At this point, we can compile and deploy the API at the Wildfly. We need just to run the following command in the root path of the API:

    #mvn clean install wildfly:deploy

Then, we can access the API using a browser, postman, or curl to interact with it. The endpoints are the following:

1: GET http://127.0.0.1:8080/PhoneBookREST/application/phonebook/contacts - get the list of all contacts;

2: GET http://127.0.0.1:8080/PhoneBookREST/application/phonebook/contacts/{id} - get all information regarding a contact;

3: DELETE: http://127.0.0.1:8080/PhoneBookREST/application/phonebook/contacts/{id} - remove the contact and all associated data;

4: PUT: http://127.0.0.1:8080/PhoneBookREST/application/phonebook/contacts/{contact_id}/{number_id} - update the phone number of a specific contact;

5: PUT: http://127.0.0.1:8080/PhoneBookREST/application/phonebook/contacts/{id}/address - update the address of a specific contact;

6: PUT: http://127.0.0.1:8080/PhoneBookREST/application/phonebook/contacts/{id} - update the contact;

7: POST: http://127.0.0.1:8080/PhoneBookREST/application/phonebook/contacts/{id}/number - Add a new number to a specific contact;

8: POST: http://127.0.0.1:8080/PhoneBookREST/application/phonebook/contacts/{id}/address - Add an address to a specific contact if the contact does not have one;

9: POST: http://127.0.0.1:8080/PhoneBookREST/application/phonebook/contacts - Add a new contact without phone numbers and address.

Examples of JSON arrays to use with the previous points at the body of messages:

    9: {
	    "name":"FILIPUS",
	    "dayBirth":"1990-01-01T00:00:00Z"
        }

    8: {
        "houseNumber":"50",
        "street":"Street aaa",
        "streetNumber":"40",
        "zipCode":"9220-215",
        "city":"COIMBRA"
        }

    7: {
        "operatorName":"NOS",
        "number":"96745211"
        }

    6: {
	    "name":"Luis",
	    "dayBirth":"2000-01-01T00:00:00Z"
        }

    5: {
        "houseNumber":"54",
        "street":"Street XYZ",
        "streetNumber":"42",
        "zipCode":"9220-225",
	    "city":"LISBOA"
        }

    4: {
	    "operatorName":"ZON_CHANGED-",
	    "number":"93232323"
        }


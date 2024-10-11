   # Event-Management-System
This is an event management system developed using Spring Boot framework. This project is managing events such as creating events and updating event. These technologies have been implemented in this project.

    1. Spring-security(JWT),
    2. caffeine for caching, 
    3. Mysql,
    4. Global Exception
    5. Swagger Explorer

# There are the following steps for the setup of the project.
    1. JDK 21 
    2. Spring-boot 3.3.4
    3. Mysql server 8.3
    
# Create the database in Mysql server.
  create database event_application
  Set the databse configuration in the properties file. Like Username, Password, URL, etc..
  
 # Start the event management system.
 ```sh
mvn spring-boot:run
```
#### 1. User registration API for the event.

 ```sh
 curl --location 'http://localhost:8088/register' \
--header 'Accept-Language: hi' \
--header 'Content-Type: application/json' \
--header 'Cookie: COOKIE_SUPPORT=true; GUEST_LANGUAGE_ID=en_US' \
--data-raw '{
  "username": "Amir",
  "password": "Amir123",
  "roles": ["Admin"]
}'
```

#### 2. User Login API for the User 
```sh
curl --location 'http://localhost:8088/authenticate' \
--header 'Accept-Language: hi' \
--header 'Content-Type: application/json' \
--header 'Cookie: COOKIE_SUPPORT=true; GUEST_LANGUAGE_ID=en_US' \
--data-raw '{
  "username": "Amir",
  "password": "Amir123"
}'
```
you will get the JWT token for the event app. 


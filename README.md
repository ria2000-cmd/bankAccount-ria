# BANK-APP

### Spring boot application that manages account

Application runs on `http://localhost:8082` 

### Database  config

URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password:

#### Build the Project
mvn clean install

#### Run the Application
mvn spring-boot:run

#### Create account api
POST /bank-app/accounts
Content-Type: application/json
{
"accountHolderName": "Ria Vaya",
"accountNumber": "123456",
"bankName": "Absa",
"balance": 100.0,
"isAccountActive": true
}


#### Get Account
GET /bank-app/accounts/{id}


#### Get Account by Number
GET /bank-app/accounts/number/{accountNumber}


#### update account
PUT /bank-app/accounts/{id}
Content-Type: application/json
{
"accountHolderName": "Jane Smith",
"bankName": "ABSA",
"balance": 2000.0
}

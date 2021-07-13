# Country Neighbors Tour

VMWare interview task

This is a maven spring boot project.  To execute:

```./mvnw spring-boot:run```

The rest api is at:

[http://localhost:8080/api/v1/requestTour](http://localhost:8080/api/v1/requestTour) 

I use postman to execute, the following is the request body to be POSTed at the api endpoint.

```json
{
    "startingCountry": "BGR",
    "budgetPerCountry": 100.00,
    "totalBudget": 1200.00,
    "currency": "EUR"
}
```
In a perfect world, the above POST will produce the following:

```json
{
  "startCountry": "BGR",
  "currency": "EUR",
  "totalBudget": 1200.00,
  "remainder": 200.00,
  "numberOfTours": 2,
  "tourCountryList": [
    {
      "country": "Greece",
      "currency": "EUR",
      "budget": 100.0000
    },
    {
      "country": "Republic of Macedonia",
      "currency": "MKD",
      "budget": 6159.8100
    },
    {
      "country": "Romania",
      "currency": "RON",
      "budget": 492.7226
    },
    {
      "country": "Serbia",
      "currency": "RSD",
      "budget": 11764.4600
    },
    {
      "country": "Turkey",
      "currency": "TRY",
      "budget": 1022.9740
    }
  ]
}
```
I have added the ability to allow the user to enter "Bulgaria" rather than BGR.

This project uses spring web client to load the required data for execution.  

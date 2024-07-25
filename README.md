# Overview

Project to manage accounts for clients.

It has the following modules

**client-service** : Microservice that expose an API REST to CRUD into app_db database information for clients.

- [README](client-service/README.md)

**movements-service** : Microservice that expose an API REST to CRUD into app_db database information for accounts and movements by account.

- [README](movements-service/README.md)

## Architecture

- Both microservices use the same database.
- Both communicate each other asynchronously by RabbitMQ queues (It has just an example).
- Both use Hibernate to create tables and Repository patron.


## How to test
- It is a requirement to have docker installed and running in order to run tests. See: https://docs.docker.com/get-started/
- In a terminal run `mvn clean install` to build and run all tests.

## Usage

- Run the application in a terminal located in main folder using

```
docker-compose up --build
```

- To delete all containers and volumes run

```
docker-compose down -v
```

- You can import postman collections to test the API's:
  - client-service: [client_collection](client-service/ClientService.postman_collection.json)
  - movements-service: [movements_collection](movements-service/MovementService.postman_collection.json)
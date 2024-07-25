# Overview

1. API REST for accounts and movements management.
    - Exposed in port 9999.

2. Receive a message with client information from a queue from client-service microservice (For asynchronous behavior).

## How to test it
- It is a requirement to have docker installed and running in order to run tests. See: https://docs.docker.com/get-started/
- In terminal run `mvn clean install` to build and run all tests.

## How it works
- Run the application in a terminal located in main folder using

```
docker-compose up --build
```

- To delete all containers and volumes run

```
docker-compose down -v
```

# Overview

1. API REST for clients management.
   - Exposed in port 8888.

2. Send a message with client information into a queue (For asynchronous behavior).
    - Endpoint: http://localhost:8888/api/async/clientes

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

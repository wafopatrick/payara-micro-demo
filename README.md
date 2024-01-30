# Demo Usage of Payara Micro with a simple Jakarta EE 10 application connected to a PostgreSQL database, all running in Docker containers.

## Prerequisites

- Docker
- Docker Compose
- Java 21
- Maven
- Git
- Your favorite IDE (I use IntelliJ IDEA)

## Structure of the repository

- `demo-rest-service`: The Jakarta EE 10 application
- `Dockerfile`: The Dockerfile for the Jakarta EE 10 application
- `docker-compose.yaml`: The Docker Compose file for the application and the PostgreSQL database
- `README.md`: This README file
- `pom.xml`: The Maven POM file for the Jakarta EE 10 application
- `src/main/java`: The Java source code of the Jakarta EE 10 application
- `src/main/resources`: The resources of the Jakarta EE 10 application including the `persistence.xml` file,
   which is used to configure the connection to the PostgreSQL database, the flyway migration script and the asadmin
   commands for the Payara Micro server. The asadmin commands are used to configure the datasource for the PostgreSQL and to 
   define the jndi name for the datasource.

## How to run

1. Clone this repository
2. Run `docker build -t demo-payara-micro .` in the root directory of the repository
3. Run `docker-compose up -d` in the root directory of the repository

## How to test

1. Run `curl -v http://localhost:8080/demo/api/employees -d '{"name":"John Doe", "position":"Software Engineer"}' -H "Content-Type: application/json" -X POST` to create a new employee
2. Run `curl -v http://localhost:8080/demo/api/employees` to get all employees
3. Run `curl -v http://localhost:8080/demo/api/employees/1` to get the employee with id 1
4. Run `curl -v http://localhost:8080/demo/api/employees/1 -d '{"name":"John Doe", "position":"Senior Software Engineer"}' -H "Content-Type: application/json" -X PUT` to update the employee with id 1
5. Run `curl -v http://localhost:8080/demo/api/employees/1 -X DELETE` to delete the employee with id 1

## How to stop

1. Run `docker-compose down` in the root directory of the repository to stop and remove the containers
2. Run `docker rmi demo-payara-micro` to remove the image
3. Run `docker volume rm demo-payara-micro_postgres-data` to remove the volume
4. Run `docker network rm demo-payara-micro_default` to remove the network

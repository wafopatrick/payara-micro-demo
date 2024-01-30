# Stage 1: Build the application
FROM --platform=linux/arm64/v8 maven:3.8.1-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package

# Stage 2: Setup Payara server with the built application
FROM --platform=linux/arm64/v8 payara/micro:latest
COPY --from=build /app/target/demo.war /opt/payara/deployments/
COPY --from=build /app/src/main/resources/asadmin-commands.txt /opt/payara/asadmin-commands.txt

# Switch to root user to install curl
USER root
RUN apk update && apk add curl

# Download PostgreSQL JDBC driver
RUN curl -L https://jdbc.postgresql.org/download/postgresql-42.2.5.jar -o /opt/payara/postgresql-42.2.5.jar

# Switch back to payara user
USER payara

# Add PostgreSQL JDBC driver to classpath and execute asadmin commands
ENTRYPOINT ["java", "-jar", "/opt/payara/payara-micro.jar", "--addJars", "/opt/payara/postgresql-42.2.5.jar", "--deploy", "/opt/payara/deployments/demo.war", "--postbootcommandfile", "/opt/payara/asadmin-commands.txt"]

# Expose ports
EXPOSE 8080

# Set environment variables
ENV POSTGRES_DB mydb
ENV POSTGRES_USER user
ENV POSTGRES_PASSWORD password
ENV POSTGRES_HOSTNAME localhost
ENV POSTGRES_PORT 5432
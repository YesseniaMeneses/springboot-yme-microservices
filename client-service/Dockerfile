FROM maven:3.8.5-openjdk-17 as builder
WORKDIR /client-service
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package -DskipTests=true

FROM openjdk:23-slim as prod
RUN mkdir /client-service
COPY --from=builder /client-service/target/*.jar client-service.jar
#WORKDIR /client-service
EXPOSE 8888
ENTRYPOINT ["java","-jar","/client-service.jar"]
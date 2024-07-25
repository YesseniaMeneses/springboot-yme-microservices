FROM maven:3.8.5-openjdk-17 as builder
WORKDIR /movements-service
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package -DskipTests=true

FROM openjdk:23-slim as prod
RUN mkdir /movements-service
COPY --from=builder /movements-service/target/*.jar movements-service.jar
#WORKDIR /movements-service
EXPOSE 8888
ENTRYPOINT ["java","-jar","/movements-service.jar"]
#FROM ringcentral/jdk:latest
#WORKDIR /app
#COPY target/CLNotesV1-0.0.1-SNAPSHOT.jar clnotes.jar
#EXPOSE 8090
#ENTRYPOINT ["java","-jar","clnotes.jar"]

FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
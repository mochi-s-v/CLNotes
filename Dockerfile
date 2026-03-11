FROM ringcentral/jdk:latest
WORKDIR /app
COPY target/CLNotesV1-0.0.1-SNAPSHOT.jar clnotes.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","clnotes.jar"]
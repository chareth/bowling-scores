FROM maven:3.5-jdk-8 AS build  
COPY src /src/app/src
COPY pom.xml /src/app 
COPY config.yml /src/app 
RUN mvn -f /src/app/pom.xml clean package -DskipTests
RUN ls /src/app/target
RUN ls /src/app

FROM gcr.io/distroless/java  
COPY --from=build /src/app/target/bowling-scores-1.0-SNAPSHOT.jar /app/bowling-scores-1.0-SNAPSHOT.jar
COPY --from=build /src/app/config.yml /app
EXPOSE 8080
EXPOSE 8081 
ENTRYPOINT ["java","-jar","/app/bowling-scores-1.0-SNAPSHOT.jar", "server", "/app/config.yml"]  

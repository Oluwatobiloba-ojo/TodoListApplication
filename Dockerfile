FROM maven:3.8.5-openjdk-21 AS build
COPY ../.. .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/todoLists-1.0-SNAPSHOT.jar todoLists.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "todoLists.jar"]

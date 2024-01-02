FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/todoLists-1.0.0-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 9005
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]

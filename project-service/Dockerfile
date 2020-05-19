FROM openjdk:8-jre-slim
COPY ./target/project-service-1.0.0-SNAPSHOT.jar /usr/src/app/
WORKDIR /usr/src/app
EXPOSE 8080
CMD ["java", "-jar", "project-service-1.0.0-SNAPSHOT.jar"]
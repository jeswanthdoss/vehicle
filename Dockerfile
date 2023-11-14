FROM openjdk:17-alpine
EXPOSE 8082
ADD target/vehicle-app.jar vehicle-app.jar
ENTRYPOINT ["java","-jar","/vehicle-app.jar"]
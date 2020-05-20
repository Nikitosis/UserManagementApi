FROM openjdk:8u121-jre-alpine

COPY target/userRequest*.jar /data/userRequest.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/data/userRequest.jar"]
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
EXPOSE 8082
COPY target/*.jar spring-boot-client.jar
ENTRYPOINT ["java","-jar","/spring-boot-client"]


FROM openjdk:8-jdk-alpine
MAINTAINER xiaokexiang
VOLUME /tmp
ADD target/spring-cloud-consul-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8001
ENTRYPOINT ["java","-jar","/app.jar"]
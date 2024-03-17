FROM openjdk:18-jdk-alpine
#FROM openjdk:17-buster
MAINTAINER Andrey Tarasov
VOLUME /jirarush
COPY target/jira-1.0.jar jira-1.0.jar
COPY resources ./resources
ENTRYPOINT ["java","-jar","/jira-1.0.jar"]
FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=SpringBank-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} spring-bank.jar
#COPY target/SpringBank-1.0-SNAPSHOT.jar spring-bank.jar
#EXPOSE 8080
ENTRYPOINT ["java","-jar","spring-bank.jar"]

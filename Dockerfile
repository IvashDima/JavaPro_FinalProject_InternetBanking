FROM openjdk:17-jdk-slim
WORKDIR /app
#VOLUME /tmp
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} spring-bank.jar
#COPY target/SpringBank-1.0-SNAPSHOT.jar spring-bank.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","spring-bank.jar"]

#LABEL authors="divashchenko"
#WORKDIR /app
#COPY target/SpringBank-1.0-SNAPSHOT.jar app.jar
#CMD ["java", "-jar", "app.jar"]
FROM openjdk:22-jdk
LABEL maintainer="isly7959@gmail.com"
RUN mkdir /app
COPY build/libs/CRMproject-0.0.1-SNAPSHOT.jar /app/CRMproject-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/app/CRMproject-0.0.1-SNAPSHOT.jar"]
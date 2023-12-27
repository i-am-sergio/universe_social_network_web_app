FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY .env .

COPY target/backend-0.0.1-SNAPSHOT.war .

EXPOSE 5000

CMD ["java", "-jar", "backend-0.0.1-SNAPSHOT.war"]

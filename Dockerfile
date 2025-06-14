FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY . .

RUN ./gradlew build -x test

EXPOSE 8888

CMD ["java", "-jar", "build/libs/config-server.jar"]

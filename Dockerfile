FROM gradle:9.1-jdk25-alpine as build
WORKDIR /app
COPY . .
run gradle build --no-daemon

FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY --from=build /app/build/libs/*.jar  /app/agendador-tarefas.jar

EXPOSE 8081

CMD ["java", "-jar", "/app/agendador-tarefas.jar"]
FROM openjdk:17-ea-14-jdk-alpine3.13 as builder
RUN mkdir /app
WORKDIR /app
COPY . .
RUN ./mvnw package

FROM openjdk:17-ea-14-alpine3.13
RUN mkdir /app
WORKDIR /app
COPY --from=builder /app/target/*.jar powerjobs-spring.jar
CMD ["java", "-jar", "powerjobs-spring.jar"]
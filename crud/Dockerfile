FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /builder

COPY . /builder/

RUN mvn clean package

FROM amazoncorretto:17-alpine3.16

WORKDIR /app

COPY --from=build /builder/target/*.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]
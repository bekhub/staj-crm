FROM alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
COPY frontend frontend

RUN apk add --update npm openjdk11
RUN ./mvnw install -DskipTests
CMD [ "./mvnw", "spring-boot:run" ]
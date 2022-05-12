FROM gradle:jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test --no-daemon

FROM openjdk:17.0-jdk-slim
RUN mkdir /app
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/service2.jar .
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Djava.security.egd=file:/dev/./urandom", "-jar", "service2.jar"]
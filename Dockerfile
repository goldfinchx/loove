# BUILD
FROM gradle:latest as build

ARG MODULE_NAME

COPY ./build.gradle /home/gradle
COPY ./settings.gradle /home/gradle
COPY ./${MODULE_NAME} /home/gradle/${MODULE_NAME}

WORKDIR /home/gradle

RUN gradle :${MODULE_NAME}:bootJar

RUN mv /home/gradle/${MODULE_NAME}/build/libs/*.jar /app.jar

# RUN
FROM alpine/java:21-jdk as run
RUN adduser -S run
USER run

COPY --from=build /app.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
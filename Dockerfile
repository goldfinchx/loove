# BUILD
FROM gradle:latest AS build

ARG MODULE_NAME
RUN echo "MODULE_NAME: ${MODULE_NAME}"

COPY ./build.gradle /home/gradle
COPY ./settings.gradle /home/gradle
COPY ./${MODULE_NAME} /home/gradle/${MODULE_NAME}

WORKDIR /home/gradle

RUN gradle :${MODULE_NAME}:clean
RUN gradle :${MODULE_NAME}:bootJar

RUN mv /home/gradle/${MODULE_NAME}/build/libs/*.jar /app.jar

# RUN
FROM alpine/java:21-jdk AS run
RUN adduser -S run
USER run

COPY --from=build app.jar .

ENTRYPOINT ["java", "-jar", "app.jar"]
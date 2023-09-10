FROM gradle:jdk17-alpine AS builder
COPY --chown=gradle:gradle . /gradle
WORKDIR /gradle
RUN gradle build --no-daemon

FROM openjdk:17-alpine
RUN mkdir /app
COPY --from=builder /gradle/build/libs/*.jar /app/app.jar
ENV SPRING_PROFILES_ACTIVE ${SPRING_PROFILES_ACTIVE}
ENV DB_URL ${DB_URL}
ENV DB_USERNAME ${DB_USERNAME}
ENV DB_PASSWORD ${DB_PASSWORD}
ENTRYPOINT ["java",  "-jar", "/app/app.jar"]
EXPOSE 8080

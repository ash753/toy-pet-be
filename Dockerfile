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
ENV KAKAO_CLIENT_ID ${KAKAO_CLIENT_ID}
ENV KAKAO_CLIENT_SECRET ${KAKAO_CLIENT_SECRET}
ENV KAKAO_REDIRECT_URI ${KAKAO_REDIRECT_URI}
ENV JWT_SECRET_KEY ${JWT_SECRET_KEY}
ENTRYPOINT ["java",  "-jar", "/app/app.jar"]
EXPOSE 8080

FROM openjdk:8-jdk-alpine
LABEL maintainer="tanks"
VOLUME /tmp
EXPOSE 8090
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar", "$@"]
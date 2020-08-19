FROM openjdk:8-jdk-alpine
LABEL maintainer="tanks"
VOLUME /tmp
EXPOSE 8090
ARG JAR_FILE=target/*.war
ADD ${JAR_FILE} app.war
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.war", "$@"]
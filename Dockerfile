FROM eclipse-temurin:21-jdk-alpine

WORKDIR workspace

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} find-directions.jar

ENV TZ=Asia/Seoul

ENTRYPOINT ["java", "-jar", "find-directions.jar"]
FROM openjdk:11-jre
ARG JAR_FILE
ENTRYPOINT ["java", "-jar", "/usr/share/gateway-service/service.jar"]
ADD target/${JAR_FILE} /usr/share/gateway-service/service.jar

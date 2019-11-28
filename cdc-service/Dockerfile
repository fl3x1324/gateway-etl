FROM adoptopenjdk/openjdk13-openj9:x86_64-alpine-jre13u-nightly
ARG JAR_FILE
ENTRYPOINT ["java", "--enable-preview", "-jar", "/usr/share/gateway-cdc-service/service.jar"]
ADD target/${JAR_FILE} /usr/share/gateway-cdc-service/service.jar

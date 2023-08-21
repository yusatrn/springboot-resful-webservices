FROM eclipse-temurin:17
LABEL mentainer="white@gmail.com"
WORKDIR /app
COPY target/springboot-resful-webservices-0.0.1-SNAPSHOT.jar /app/springboot-resful-webservices.jar
ENTRYPOINT ["java", "-jar", "springboot-resful-webservices.jar"]
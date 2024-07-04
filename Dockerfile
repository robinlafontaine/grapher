FROM amazoncorretto:17-al2023-jdk
MAINTAINER "Robin"
COPY target/grapher-1.0.0.jar grapher-1.0.0.jar
ENTRYPOINT ["java","-jar","/grapher-1.0.0.jar"]
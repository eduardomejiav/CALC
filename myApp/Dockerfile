FROM openjdk:8-jdk-alpine
VOLUME ["/tmp"]
ADD ./target/myapp-1.0.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container","-jar","/app.jar"]
CMD ["/bin/sh"]

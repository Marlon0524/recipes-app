FROM openjdk:17
VOLUME /tmp
EXPOSE 8020
ADD ./target/recipes-0.0.1-SNAPSHOT.jar recipes-app.jar
ENTRYPOINT ["java", "-jar", "recipes-app.jar"]
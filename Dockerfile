FROM java:8-jre
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

ADD build/libs/*.jar app.jar

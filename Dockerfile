FROM openjdk:17-jdk

VOLUME /tmp

# Копируйте JAR файл в контейнер
ARG JAR_FILE=target/leon_scrapping-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} leon_scrapping.jar

# Запустите приложение
ENTRYPOINT ["java", "-jar", "/leon_scrapping.jar"]
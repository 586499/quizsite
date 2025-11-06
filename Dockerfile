# 使用 JDK 21
FROM eclipse-temurin:21-jdk-alpine

# 複製 Spring Boot JAR 到容器
COPY target/quiz-0.0.1-SNAPSHOT.jar app.jar

# 開放 8080 埠口
EXPOSE 8080

# 啟動 Spring Boot 網站
ENTRYPOINT ["java", "-jar", "/app.jar"]

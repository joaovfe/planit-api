# Etapa de build
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

COPY . .

# Dá permissão de execução ao wrapper do Maven
RUN chmod +x ./mvnw

# Compila o projeto
RUN ./mvnw clean package -DskipTests

# Etapa final
FROM eclipse-temurin:17-jre AS runner

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8000
ENV PORT=8000

ENTRYPOINT ["java", "-jar", "app.jar"]

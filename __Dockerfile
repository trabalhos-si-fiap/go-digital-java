# Etapa 1: Build da aplicação
FROM maven:3.9.9-eclipse-temurin-21 AS builder

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo pom.xml e baixa as dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o restante do código fonte
COPY src ./src

# Compila e empacota a aplicação
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final para rodar a aplicação
FROM eclipse-temurin:21-jdk-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o jar gerado na etapa anterior
COPY --from=builder /app/target/laros-0.0.1.jar /app/app.jar

# Exponha a porta na qual a aplicação rodará (modifique se necessário)
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

# Utilizar uma base com Temurin
FROM eclipse-temurin:21-jdk-alpine

# Instalar Maven e ferramentas adicionais
RUN apk update && apk add --no-cache bash maven

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo pom.xml e baixa as dependências para cache no container
COPY pom.xml /app/
RUN mvn dependency:go-offline -B

# Montar a aplicação no container durante a execução
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.fork=false"]
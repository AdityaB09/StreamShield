# ---------- Build ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Pre-fetch deps
COPY pom.xml .
RUN mvn -q -B -e -DskipTests dependency:go-offline

# Build
COPY src ./src
RUN mvn -q -B -DskipTests package

# ---------- Runtime ----------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# OpenNLP models (lightweight; can be swapped later)
RUN apk add --no-cache curl && mkdir -p /app/models
RUN curl -sL https://dlcdn.apache.org/opennlp/models/lang/en/1.5/en-ner-person.bin -o /app/models/en-ner-person.bin && \
    curl -sL https://dlcdn.apache.org/opennlp/models/lang/en/1.5/en-ner-organization.bin -o /app/models/en-ner-organization.bin && \
    curl -sL https://dlcdn.apache.org/opennlp/models/lang/en/1.5/en-ner-location.bin -o /app/models/en-ner-location.bin

# ⬇️ IMPORTANT: jar name is 'api.jar', not 'api-*.jar'
COPY --from=build /app/target/api.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]

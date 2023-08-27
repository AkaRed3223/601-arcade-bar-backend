FROM maven:3.8.3-openjdk-17-slim AS builder
WORKDIR /arcade
COPY . .

RUN mvn package

FROM maven:3.8.3-openjdk-17-slim AS runner
WORKDIR /arcade
EXPOSE 8080
COPY --from=builder /arcade/target/arcade-app-docker.jar .
CMD java -jar arcade-app-docker.jar
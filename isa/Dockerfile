ARG BUILD_IMAGE=maven:3.8.1-openjdk-17-slim
ARG RUNTIME_IMAGE=openjdk:17-jdk-slim-buster

#############################################################################################
###                Stage where Docker is pulling all maven dependencies                   ###
#############################################################################################
FROM ${BUILD_IMAGE} as dependencies

ARG PROXY_SET=false

COPY pom.xml ./

RUN mvn -B dependency:go-offline \
  -DproxySet=${PROXY_SET}
#############################################################################################

#############################################################################################
###              Stage where Docker is building spring boot app using maven               ###
#############################################################################################
FROM dependencies as build

ARG PROXY_SET=false

COPY src ./src

RUN mvn -B clean package \
  -DskipTests \
  -DproxySet=${PROXY_SET}
#############################################################################################

#############################################################################################
### Stage where Docker is running a java process to run a service built in previous stage ###
#############################################################################################
FROM ${RUNTIME_IMAGE}

COPY --from=build /target/isa-*.jar /app/isa.jar

ENTRYPOINT java -jar /app/isa.jar
#############################################################################################
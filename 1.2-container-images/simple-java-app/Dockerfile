#----------------------------------------------------------------
# Setting global VARs
#----------------------------------------------------------------
# Optional ARGS

ARG BUILDER_APP_PATH=./target/simple-java-app*.jar
ARG BUILDER_WORK_DIR=/app
ARG APPLICATION_RUNTIME_DIR=/app

# Local ARGS
ARG BUILDER_APP_ROOT_DIR=$BUILDER_WORK_DIR/appsource

#----------------------------------------------------------------
# Building 1st stage:
#
# this stage will build the application with Maven
#----------------------------------------------------------------
FROM arm64v8/eclipse-temurin:21 as build

ARG BUILDER_APP_ROOT_DIR

# Set the working directory in the container
WORKDIR $BUILDER_APP_ROOT_DIR

# Copy the project files to the container
COPY . ./

# Build the application using Maven
RUN ./mvnw clean package -DskipTests

#----------------------------------------------------------------
# Building 2nd stage:
#
# this stage will extract the layers of the application
#----------------------------------------------------------------
FROM arm64v8/eclipse-temurin:21 as builder

ARG BUILDER_APP_ROOT_DIR
ARG BUILDER_APP_PATH
ARG BUILDER_WORK_DIR

WORKDIR $BUILDER_WORK_DIR

COPY --from=build ${BUILDER_APP_ROOT_DIR}/${BUILDER_APP_PATH} application.jar

RUN java -Djarmode=layertools -jar application.jar extract \
    && rm -f application/BOOT-INF/classes/application-*.properties

#----------------------------------------------------------------
# Building final stage:
#
# the final stage of our build will copy the extracted layers
#----------------------------------------------------------------
FROM arm64v8/eclipse-temurin:21 as springapp

ARG APPLICATION_RUNTIME_DIR

WORKDIR $APPLICATION_RUNTIME_DIR

COPY --from=builder $APPLICATION_RUNTIME_DIR/dependencies/ ./
COPY --from=builder $APPLICATION_RUNTIME_DIR/spring-boot-loader/ ./
COPY --from=builder $APPLICATION_RUNTIME_DIR/snapshot-dependencies/ ./
COPY --from=builder $APPLICATION_RUNTIME_DIR/application/ ./

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS org.springframework.boot.loader.launch.JarLauncher"]
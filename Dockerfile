# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

# Runtime stage
FROM omnifish/glassfish:7.0.15
ENV GLASSFISH_HOME=/opt/glassfish7

# Database Configuration
ARG ORACLE_PDB=MEDPDB
ARG ORACLE_PWD=12345
ARG DB_PORT=1521
ARG DB_USER=medadmin
ARG DB_PASSWORD=12345
ARG DB_HOST=db

# Set as environment variables
ENV ORACLE_PDB=${ORACLE_PDB} \
    ORACLE_PWD=${ORACLE_PWD} \
    DB_PORT=${DB_PORT} \
    DB_USER=${DB_USER} \
    DB_PASSWORD=${DB_PASSWORD} \
    DB_HOST=${DB_HOST} \
    APP_ENV=production

# Copy Oracle JDBC driver
USER root
ADD --chmod=644 https://repo1.maven.org/maven2/com/oracle/database/jdbc/ojdbc11/23.4.0.24.05/ojdbc11-23.4.0.24.05.jar ${GLASSFISH_HOME}/glassfish/domains/domain1/lib/

# Copy deployment artifacts with explicit permissions
COPY --from=build --chmod=644 /app/target/ROOT.war ${GLASSFISH_HOME}/glassfish/domains/domain1/autodeploy/

# Copy the configuration script
COPY --chmod=755 docker/glassfish_setup.sh ${GLASSFISH_HOME}/bin/

# Execute setup mode during build (no blocking)
RUN ${GLASSFISH_HOME}/bin/glassfish_setup.sh setup

# Expose ports
EXPOSE 8080 4848

# Start GlassFish with configuration
CMD /opt/glassfish7/bin/glassfish_setup.sh run
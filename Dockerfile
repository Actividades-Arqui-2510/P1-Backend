# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

# Runtime stage
FROM glassfish/server:7.0.0

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
ADD https://repo1.maven.org/maven2/com/oracle/database/jdbc/ojdbc11/23.4.0.24.05/ojdbc11-23.4.0.24.05.jar ${GLASSFISH_HOME}/glassfish/domains/domain1/lib/

# Copy deployment artifacts
COPY --from=build /app/target/ROOT.war ${GLASSFISH_HOME}/glassfish/domains/domain1/autodeploy/

# Copy the configuration script
COPY docker/glassfish_setup.sh ${GLASSFISH_HOME}/bin/
RUN chmod +x ${GLASSFISH_HOME}/bin/glassfish_setup.sh

# Expose ports
EXPOSE 8080

# Start GlassFish with configuration
CMD ["${GLASSFISH_HOME}/bin/glassfish_setup.sh"]
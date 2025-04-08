#!/bin/bash
set -e  # Exit on error for better debugging

# Determine operation mode
MODE=${1:-"run"}  # Default: "run" mode

# Function to configure resources
setup_resources() {
  # Start GlassFish in background
  ${GLASSFISH_HOME}/bin/asadmin start-domain

  # Wait for server to start
  sleep 10

  # Configure JDBC connection pool and resources
  if ! ${GLASSFISH_HOME}/bin/asadmin list-jdbc-connection-pools | grep -q "OraclePool" ; then
    echo "Creating JDBC connection pool..."
    ${GLASSFISH_HOME}/bin/asadmin create-jdbc-connection-pool \
      --datasourceclassname oracle.jdbc.pool.OracleDataSource \
      --restype javax.sql.DataSource \
      --property "user=${DB_USER}:password=${DB_PASSWORD}:url=jdbc\\:oracle\\:thin\\:@//${DB_HOST}\\:${DB_PORT}/${ORACLE_PDB}" \
      OraclePool
  fi

  if ! ${GLASSFISH_HOME}/bin/asadmin list-jdbc-resources | grep -q "jdbc/OraclePool" ; then
    echo "Creating JDBC resource jdbc/OraclePool..."
    ${GLASSFISH_HOME}/bin/asadmin create-jdbc-resource --connectionpoolid OraclePool jdbc/OraclePool
  fi

  if ! ${GLASSFISH_HOME}/bin/asadmin list-jdbc-resources | grep -q "jdbc/OracleDS" ; then
    echo "Creating JDBC resource jdbc/OracleDS..."
    ${GLASSFISH_HOME}/bin/asadmin create-jdbc-resource --connectionpoolid OraclePool jdbc/OracleDS
  fi

  # Stop domain to apply changes
  ${GLASSFISH_HOME}/bin/asadmin stop-domain
}

# Execute based on selected mode
if [ "$MODE" = "setup" ]; then
  # Only configure resources and exit
  setup_resources
  echo "Setup completed successfully"
else
  # Configure resources and then start in production mode
  setup_resources
  # Restart GlassFish in foreground
  exec ${GLASSFISH_HOME}/bin/asadmin start-domain --verbose
fi
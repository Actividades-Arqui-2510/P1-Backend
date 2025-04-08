#!/bin/bash
set -e  # Exit on error for better debugging

# Determine operation mode
MODE=${1:-"run"}  # Default: "run" mode

# Function to configure JDBC resources
setup_resources() {
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
}

# Function to deploy application
deploy_application() {
  echo "Deploying application..."
  if [ -f ${GLASSFISH_HOME}/glassfish/domains/domain1/autodeploy/ROOT.war ]; then
    # Undeploy first if exists
    ${GLASSFISH_HOME}/bin/asadmin undeploy ROOT || true
    # Deploy the application with context root "/"
    ${GLASSFISH_HOME}/bin/asadmin deploy --contextroot / --name ROOT ${GLASSFISH_HOME}/glassfish/domains/domain1/autodeploy/ROOT.war
    echo "Application deployed successfully"
  else
    echo "WARNING: Application WAR file not found"
  fi
}

# Execute based on selected mode
if [ "$MODE" = "setup" ]; then
  # Start GlassFish in background
  echo "Starting GlassFish for setup..."
  ${GLASSFISH_HOME}/bin/asadmin start-domain
  
  # Wait for server to start
  sleep 10
  
  # Only setup resources, don't try to deploy
  setup_resources
  
  # Stop domain to apply changes
  ${GLASSFISH_HOME}/bin/asadmin stop-domain
  echo "Setup completed successfully"
else
  # Runtime mode: Start GlassFish and deploy the application
  echo "Starting GlassFish for runtime mode..."
  ${GLASSFISH_HOME}/bin/asadmin start-domain
  
  # Wait for server to start
  sleep 10
  
  # Setup resources
  setup_resources
  
  # Try to ping database before deployment
  echo "Waiting for database to be available..."
  retry_count=0
  max_retries=5
  
  while [ $retry_count -lt $max_retries ]; do
    if ${GLASSFISH_HOME}/bin/asadmin ping-connection-pool OraclePool; then
      echo "Database connection successful"
      # Now deploy the application
      deploy_application
      break
    else
      echo "Database not available yet, waiting..."
      retry_count=$((retry_count + 1))
      sleep 10
    fi
  done
  
  # Even if database is not available, try to deploy anyway
  if [ $retry_count -eq $max_retries ]; then
    echo "WARNING: Database not available after retries, attempting deployment anyway"
    deploy_application
  fi
  
  # Stop and restart GlassFish to apply all changes
  ${GLASSFISH_HOME}/bin/asadmin stop-domain
  
  # Restart GlassFish in foreground
  exec ${GLASSFISH_HOME}/bin/asadmin start-domain --verbose
fi
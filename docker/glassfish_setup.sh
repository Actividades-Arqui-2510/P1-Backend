#!/bin/bash
# Start GlassFish in the background
${GLASSFISH_HOME}/bin/asadmin start-domain

# Wait for GlassFish to start
sleep 10

# Check if connection pool already exists
if ! ${GLASSFISH_HOME}/bin/asadmin list-jdbc-connection-pools | grep -q "OraclePool" ; then
  echo "Creating JDBC connection pool..."
  ${GLASSFISH_HOME}/bin/asadmin create-jdbc-connection-pool \
    --datasourceclassname oracle.jdbc.pool.OracleDataSource \
    --restype javax.sql.DataSource \
    --property "user=${DB_USER}:password=${DB_PASSWORD}:url=jdbc\:oracle\:thin\:@//${DB_HOST}:${DB_PORT}/${ORACLE_PDB}" \
    OraclePool
fi

# Check if JDBC resources exist
if ! ${GLASSFISH_HOME}/bin/asadmin list-jdbc-resources | grep -q "jdbc/OraclePool" ; then
  echo "Creating JDBC resource jdbc/OraclePool..."
  ${GLASSFISH_HOME}/bin/asadmin create-jdbc-resource --connectionpoolid OraclePool jdbc/OraclePool
fi

if ! ${GLASSFISH_HOME}/bin/asadmin list-jdbc-resources | grep -q "jdbc/OracleDS" ; then
  echo "Creating JDBC resource jdbc/OracleDS..."
  ${GLASSFISH_HOME}/bin/asadmin create-jdbc-resource --connectionpoolid OraclePool jdbc/OracleDS
fi

# Stop GlassFish
${GLASSFISH_HOME}/bin/asadmin stop-domain

# Start in foreground mode
exec ${GLASSFISH_HOME}/bin/asadmin start-domain --verbose
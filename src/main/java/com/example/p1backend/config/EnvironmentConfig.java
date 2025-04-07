package com.example.p1backend.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

@ApplicationScoped
public class EnvironmentConfig {

    private static final String ENV_VAR_NAME = "APP_ENV";
    private static final String PRODUCTION_ENV = "production";

    @Produces
    @Named("isProduction")
    public boolean isProduction() {
        String env = System.getenv(ENV_VAR_NAME);
        if (env == null) {
            return false;
        }
        return env.equalsIgnoreCase(PRODUCTION_ENV);
    }
    
    @Produces
    @Named("dbHost")
    public String getDbHost() {
        return isProduction() ? 
            System.getenv("DB_HOST") : "localhost";
    }
    
    @Produces
    @Named("dbPort")
    public String getDbPort() {
        return isProduction() ? 
            System.getenv("DB_PORT") : "1521";
    }
    
    @Produces
    @Named("dbName")
    public String getDbName() {
        return isProduction() ? 
            System.getenv("ORACLE_PDB") : "MEDPDB";
    }
    
    @Produces
    @Named("dbUser")
    public String getDbUser() {
        return isProduction() ? 
            System.getenv("DB_USER") : "medadmin";
    }
    
    @Produces
    @Named("dbPassword")
    public String getDbPassword() {
        return isProduction() ? 
            System.getenv("DB_PASSWORD") : "12345";
    }
}
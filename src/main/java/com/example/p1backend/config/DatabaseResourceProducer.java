package com.example.p1backend.config;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.sql.DataSource;

@ApplicationScoped
public class DatabaseResourceProducer {

    @Resource(lookup = "jdbc/OraclePool")
    private DataSource dataSource;
    
    @Inject
    @Named("isProduction")
    private boolean isProduction;

    @Produces
    @ApplicationScoped
    @Named("appDataSource")
    public DataSource produceDataSource() {
        return dataSource;
    }
}
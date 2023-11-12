package com.dcfB.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfiguration {

    public FlywayConfiguration(DataSource dataSource) {
        Flyway.configure().baselineOnMigrate(true).schemas("records_db").dataSource(dataSource).load().migrate();
    }
}
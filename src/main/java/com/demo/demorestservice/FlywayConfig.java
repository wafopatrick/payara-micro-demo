package com.demo.demorestservice;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

@Singleton
@Startup
public class FlywayConfig {

    @Resource(lookup = "jdbc/mydb")
    private DataSource dataSource;

    @PostConstruct
    public void onStartup() {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .load();
        flyway.migrate();
    }
}
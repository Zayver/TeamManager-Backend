package com.jnz.teamManager.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.DriverManager;

@Configuration
public class DatabaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);


    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Autowired
    private DataSourceProperties dataSourceProperties;


    @Bean
    public DataSource dataSource(){
        try{
            DriverManager.getConnection(dataSourceUrl, dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
            logger.info("Connected to primary database");
        } catch (Exception e) {
            logger.error("Failed to connect to primary Database, using h2 fallback. "+ e.getMessage());
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .build();
        }
        return DataSourceBuilder.create()
                .url(dataSourceUrl)
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .build();
    }
}

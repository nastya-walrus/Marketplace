package org.example.marketplace.utils;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author Shlokov Andrey
 */
@Configuration
@Profile("test")
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String connectionUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(connectionUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    @Bean
    public DatabaseConfigBean databaseConfigBean() {
        DatabaseConfigBean databaseConfigBean = new DatabaseConfigBean();
        databaseConfigBean.setCaseSensitiveTableNames(Boolean.FALSE);
        databaseConfigBean.setDatatypeFactory(new PostgresqlDataTypeFactory());
        return databaseConfigBean;
    }

    @Bean(name = "dbUnitConnection")
    public DatabaseDataSourceConnectionFactoryBean dbUnitConnection() {
        DatabaseDataSourceConnectionFactoryBean connection = new DatabaseDataSourceConnectionFactoryBean();
        connection.setDataSource(dataSource());
        connection.setDatabaseConfig(databaseConfigBean());
        connection.setSchema("public");
        return connection;
    }

}

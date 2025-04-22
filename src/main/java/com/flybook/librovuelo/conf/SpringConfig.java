package com.flybook.librovuelo.conf;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

import static java.util.Objects.nonNull;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.flybook.librovuelo")
public class SpringConfig implements WebMvcConfigurer {

    @Bean
    @Profile({"!local"})
    public DataSource dataSource(@Value("${spring.datasource.driver-class-name}") String driver,
                                 @Value("${spring.datasource.username}") String user,
                                 @Value("${spring.datasource.password}") String password,
                                 @Value("${spring.datasource.url}") String url,
                                 @Value("${spring.datasource.max-pool-size}") Integer poolSize) {

        return getHikariDataSource(driver, user, password, url, poolSize);
    }

    @Bean
    @Profile({"local"})
    public DataSource dataSourceLocal(@Value("${spring.datasource.driver-class-name}") String driver,
                                      @Value("${spring.datasource.username}") String user,
                                      @Value("${spring.datasource.password}") String password,
                                      @Value("${spring.datasource.url}") String url,
                                      @Value("${spring.datasource.max-pool-size}") Integer poolSize) {

        return getHikariDataSource(driver, user, password, url, poolSize);
    }

    private static HikariDataSource getHikariDataSource(String driver, String user, String password, String url, Integer poolSize) {

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(poolSize);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:uploads/");
    }
}

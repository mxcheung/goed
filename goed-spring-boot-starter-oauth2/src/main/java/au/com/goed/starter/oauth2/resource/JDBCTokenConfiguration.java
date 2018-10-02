package au.com.goed.starter.oauth2.resource;

import static java.util.Optional.ofNullable;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import au.com.goed.starter.oauth2.profile.JDBCToken;

/**
 * {@Code Configuration} for profiles configured to use the {@code JDBCToken}.
 * 
 * @author Goed Bezig
 *
 */
@JDBCToken
@Configuration
class JDBCTokenConfiguration {
    
    @Bean
    @ConfigurationProperties("goed.oauth2.token.jdbc")
    JDBCTokenProperties jdbcTokenProperties() {
        return new JDBCTokenProperties();
    }
    
    @Bean
    @Primary
    DefaultTokenServices tokenServices(final JDBCTokenProperties tokenProperties) {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore(tokenProperties));
        return defaultTokenServices;
    }

    @Bean
    @JDBCToken
    DataSource dataSource(final JDBCTokenProperties tokenProperties) {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(tokenProperties.getDriver());
        dataSource.setUrl(tokenProperties.getUrl());
        dataSource.setUsername(tokenProperties.getUsername());
        dataSource.setPassword(ofNullable(tokenProperties.getPassword()).orElse(""));
        return dataSource;
    }

    @Bean
    TokenStore tokenStore(final JDBCTokenProperties tokenProperties) {
        return new JdbcTokenStore(dataSource(tokenProperties));
    }
}

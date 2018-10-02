package au.com.goed.starter.flyway;

import static au.com.goed.starter.constant.Profiles.PRODUCTION;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

/**
 * <p>Configures a flyway clean and migrate profile, to conveniently execute a flyway clean followed by a migrate. 
 * This is useful for non-prod environment where you would want to recreate all schema's and data from scratch.
 * As a safety execution is disabled for `prod` profiles, although there is a bypass property
 * that can be used if someone wants to risk it.</p>
 * 
 * <p>
 * To enable `prod` profile to clean and migrate => flyway.permit.production.clean.migration = true
 * </p>
 * 
 * @author Goed Bezig
 */
@Configuration
@EnableConfigurationProperties(FlywayProperties.class)
public class FlyWayConfiguration {

    public static final String PROFILE_CLEAN_MIGRATE = "flyway-clean-migrate";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FlyWayConfiguration.class);

    @Value("${flyway.permit.production.clean.migration:false}")
    private boolean enableProductionMigration;

    /**
     * Configures a custom profiled flyway migration strategy "flyway-clean-migrate" with blockers preventing this migration strategy from
     * taking place  in a production environment unless over-ridden by property "flyway.permit.production.clean.migration".
     * 
     * @param environment current application environment.
     * @return flyway migration strategy.
     */
    @Bean
    @Autowired
    @Profile(PROFILE_CLEAN_MIGRATE)
    public FlywayMigrationStrategy migrationStrategy(Environment environment) {
        LOGGER.info("Attempting Flyway `clean and migration` on environment => "
                + asList(environment.getActiveProfiles()).stream().collect(Collectors.joining(", ")));
        if (stream(environment.getActiveProfiles()).anyMatch(PRODUCTION::equals) && !enableProductionMigration) {
            throw new RuntimeException("Unable to set flyway clean and migration strategy in a production profile (unless overridden).");
        }

        return flyway -> {
            flyway.clean();
            flyway.migrate();
        };
    }

}

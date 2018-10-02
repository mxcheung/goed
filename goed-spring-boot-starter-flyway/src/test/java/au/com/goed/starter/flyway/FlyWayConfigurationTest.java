package au.com.goed.starter.flyway;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.core.env.Environment;

import au.com.goed.starter.flyway.FlyWayConfiguration;

/**
 * Unit tests for {@link RequestLoggingFilter}.
 * 
 * @author Goed Bezig
 */
@RunWith(MockitoJUnitRunner.class)
public class FlyWayConfigurationTest {

    private FlyWayConfiguration configuration = new FlyWayConfiguration();

    @Mock
    private Environment environment;

    @Test
    public void shouldRunMigrationOnNonProdEnvironment() {
        when(environment.getActiveProfiles()).thenReturn(new String[] { "dev", "test" });
        FlywayMigrationStrategy strategy = configuration.migrationStrategy(environment);
        assertNotNull(strategy);
    }

    @Test
    public void shouldRunMigrationOnProdWhenEnableProductionMigrationIsTrue() {
        setField(configuration, "enableProductionMigration", true);
        when(environment.getActiveProfiles()).thenReturn(new String[] { "prod", "test" });
        FlywayMigrationStrategy strategy = configuration.migrationStrategy(environment);
        assertNotNull(strategy);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionWhenEnvironmentProd() {
        when(environment.getActiveProfiles()).thenReturn(new String[] { "prod", "test" });
        configuration.migrationStrategy(environment);
    }
}

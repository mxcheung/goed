package au.com.goed.starter.constant;

/**
 * <p>
 * Profile Constants reference: In aiding in profiling (spring.profiles.active) the possible base profiles
 * that will be utilized in running of a service.
 * </p>
 * <p>
 * 5 Main profiles will be in use:
 * <ol>
 * <li>prod => for production environments</li>
 * <li>uat => for user acceptance testing environments.</li>
 * <li>dev => for development environments.</li>
 * <li>local => for local development environments (developer machine).</li>
 * <li>cloud => for none of the above profiles environments wishing to run on the cloud.</li>
 * </ol>
 * </p>
 * 
 * @author Goed Bezig
 */
public final class Profiles {

    public static final String PRODUCTION = "prod";
    public static final String UAT = "uat";
    public static final String DEVELOPMENT = "dev";
    public static final String LOCAL = "local";
    public static final String CLOUD = "cloud";

    /**
     * Private constructor for utility class.
     */
    private Profiles() {
        
    }
}

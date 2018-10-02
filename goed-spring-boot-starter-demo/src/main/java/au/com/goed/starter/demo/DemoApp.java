package au.com.goed.starter.demo;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.MoreObjects.firstNonNull;
import static java.lang.String.format;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.google.common.collect.ImmutableMap;

@SpringBootApplication
public class DemoApp extends SpringBootServletInitializer {
    
    /**
     * Tool used: http://patorjk.com/software/taag/
     */
    private static final String[] BANNER = { 
        "    ____  ________  _______", 
        "   / __ \\/ ____/  |/  / __ \\",
        "  / / / / __/ / /|_/ / / / /",
        " / /_/ / /___/ /  / / /_/ /", 
        "/_____/_____/_/  /_/\\____/"  
         };

     private static final String INDENTATION = "    ";

     private static final String DEFAULT_TITLE = "goed Starter Demo";
     private static final String DEFAULT_VERSION = "1.0.0";
     private static final String DEFAULT_VENDOR = "goed Digital";
     private static final String DEFAULT_DESCRIPTION = "goed Starter Demo App.";
     
     /**
      * {@inheritDoc}
      */
     @Override
     protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
         // Obtain information where possible from Manifest file.
         final String title = firstNonNull(DemoApp.class.getPackage().getImplementationTitle(), DEFAULT_TITLE);
         final String version = firstNonNull(DemoApp.class.getPackage().getImplementationVersion(), DEFAULT_VERSION);
         final ImmutableMap.Builder<String, Object> properties = ImmutableMap.<String, Object>builder()
                 .put("application.title", title)
                 .put("application.version", version)
                 .put("application.vendor", DEFAULT_VENDOR)
                 .put("application.description", DEFAULT_DESCRIPTION);
                 
         return builder.sources(DemoApp.class)
                 .properties(properties.build())
                 .web(true)
                 .banner((environment, sourceClass, out) -> {
                     for (String line : BANNER) {
                         out.print(format("\n%s%s", INDENTATION, line));
                     }
              
                     out.print(format("\n%s%s : %s (%s)\n\n", INDENTATION, title, version, on(",").join(environment.getActiveProfiles())));
                 });
     }

     /**
      * Start entry point for the application service.
      * @param args command line args.
      */
     public static void main(String[] args) {
         final DemoApp app = new DemoApp();
         app.run(app.configure(new SpringApplicationBuilder(DemoApp.class)).build());
     }
}

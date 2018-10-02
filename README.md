# goed
| Component  | Dependencies | Comments |
| ------------- | ------------- | ------------- |
| goed-starter-profiles-constants  | N/A |  Content Cell |
| goed-spring-boot-starter-web | goed-starter-profiles-constants   | Predefined profiled annotations and constants for use across all projects |
| goed-spring-boot-starter-oauth2 | goed-starter-profiles-constants   | Services that require the use Oauth2 |
| goed-spring-boot-starter-flyway| goed-starter-profiles-constants   | Integrate `FlyWay` conveniently into a Spring Boot application |
| goed-spring-boot-starter-actuator-swagger | goed-starter-profiles-constants   | Activate swagger and an accompanying Swagger UI |
| goed-spring-boot-starter-demo | goed-spring-boot-starter-web <br> goed-spring-boot-starter-actuator-swagger <br> goed-spring-boot-starter-oauth2 | spring cloud sleuth enablement |

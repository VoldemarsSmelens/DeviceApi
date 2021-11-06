# DeviceApi
## Startup
### Requirements
- Java 11 (Added JAVA_HOME in environment variables)
- Maven 3 (Added in path)

### Execution (Two options)
- Run StartUp.bat
- From project root folder run in CLI: mvn spring-boot:run -Dspring-boot.run.profiles=dev

### Links for application
- Swagger: http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
  - User: user
  - Password: user1234
- H2 db: http://localhost:8080/h2console/login.jsp
  - User: db
  - Password: db1234


### Dependency

```
JDK         21
Spring Boot 3.3.0
Gradle
```

### ENV

in /src/main/resources/application-{env}.properties

```
# Spring Application
spring.application.name=healthboy
server.port=4000
spring.jpa.open-in-view=true
logging.level.org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver=ERROR

# DevTools 설정
spring.devtools.restart.enabled=true
spring.devtools.livereload.enabled=true

# Database
spring.datasource.url=jdbc:mysql://{FQDN}:{port}/{db_name}
spring.datasource.username={username}
spring.datasource.password=c{password}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# SSO
google.clientId=195468533034-qko9l5rfou9g8f7kof6f1a0nr3a791uo.apps.googleusercontent.com
```

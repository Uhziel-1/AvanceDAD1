# AvanceDAD1
## Estructura de Archivos
- Config Data
- Config Server
- Registry Server
- Gateway
- Microservices
## Otros
- Intellij IDEA
- Spring Boot
- Java 17
- Maven
- Jar
- 3.4.5
- `aplication.properties` → `aplication.yml`
## Config Data
### Descripción
> Aquí van las propiedades de los microservicios, registry y Gateway
### Creación
- Empty Proyect
- Nombre: config-data
## Config Server
### Descripción
> Conecta con el donde se encuentre tus propiedades, conecta con el `Config Data`.  
> En este caso con Github.
### Creación
- Spring Boot
- Nombra: config-server
- Dependencias:
    - Spring Security
    - Config Server
### Detalles
> Código del YML
```yml
server:
  port: 7070

spring:
  application:
    name: config-server
  cloud:
    config:
      server:
        git:
          uri: https://github.com/Uhziel-1/AvanceDAD1.git
          searchPaths: config-data
          default-label: main
  security:
    user:
      name: root
      password: 123456
```
> Agregar al Aplication.java
```java
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
```
> Subir al `Github` y correr el programa.
## Registry
### Descripción
> Aquí se tiene un registro de los servicios que están corriendo.
### Cración
- Spring Boot
- Nombre: ms-registry-server
- Dependencias:
    - Config Client
    - Eureka Server
### Detalles
> Código YML
```yml
spring:
  application:
    name: ms-registry-server
  profiles:
    active: development
  config:
    import: optional:configserver:http://root:123456@localhost:7070
```
> Agregar al Aplication.java
```java
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
```
> Config Data crear `ms-registry-server.yml`
```yml
server:
  port: 8090
eureka:
  instance:
    hostname: localhost
  client:
    registerWithEureka: false
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```
> Subir al `Github` y correr el programa.
## Gateway
### Descripción
Recibira las solicitudes y se conectará con el necesario.
### Creación
- Spring Boot
- Nombra: ms-gateway-service
- Dependencias:
    - Config Client
    - Gateway
    - Eureka Discovery Client
### Detalles
> Quitar `-mvc` de la dependencia `Gateway`  
> Código del YML
```yml
spring:
  application:
    name: ms-gateway-service
  profiles:
    active: development
  config:
    import: optional:configserver:http://root:123456@localhost:7070
```
> Config Data crear `ms-gateway-service.yml`
```yml
server:
  port: 8085

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8090/eureka
  instance:
    hostname: localhost
spring:
  cloud:
    gateway:
      discovery:
        locator:
          enable: true
      routes:
```
> Subir al `Github` y correr el programa.
## Microservicio
### Descripción
Servicio es un componente independiente, pequeño y especializado de una aplicación, diseñado para realizar una tarea específica
### Creación
- Spring Boot
- Nombre: ms-[nombre]-service
- Dependencias:
    - Spring Web
    - Config Client
    - Eureka Discovery Client
- Otras Dependencias:
    - Lombok
    - Spring Data JPA
    - Validation
- Más Dependencias:
    - H2 Database
### Detalles
> Añadir estas Dependencias
> `mysql-connector-java`
```xml
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>8.0.28</version>
</dependency>
```
> `springdoc-openapi-starter-webmvc-ui`
```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.0.2</version>
</dependency>
```
> Código del YML
```yml
spring:
  application:
    name: ms-[nombre]-service
  profiles:
    active: development
  config:
    import: optional:configserver:http://root:123456@localhost:7070
```
> Estructura del Microservicios
```yml
config:
  OpenApiConfig.java
controller:
  Controller.java
entity:
  .java
repository:
  Repository.java
service:
  impl:
    ServiceImpl.java
  Service.java
util:
  Seeder.java

```
> Config Data crear `ms-[nombre]-service.yml`
```yml
server:
  port: 8081

spring:
  application:
    name: ms-[nombre]-service
  datasource:
    url: jdbc:h2:mem:ms_[nombre]
    driver-class-name: org.h2.Driver
    username: sa
    password: password
    jpa:
      database-platform: org.hibernate.dialect.H2Dialect
    h2:
      console:
        enabled: true
        path: /h2-console

springdoc:
  api-docs:
    enabled: true
  swagger-ui:
    enabled: true
    path: /doc/swagger-ui.html

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8090/eureka
  instance:
    hostname: localhost
```
> Subir al `Github` y correr el programa.
## Otros
### Microservicio → Microservicio
#### Descripción
Un microservicio necesita datos de otro microservicio
#### Creación
- Spring Boot
- Nombre: ms-[nombre]-service
- Dependencias:
    - Spring Web
    - Config Client
    - Eureka Discovery Client
    - OpenFeing
    - Resilience4J
- Otras Dependencias:
    - Lombok
    - Spring Data JPA
    - Validation
- Más Dependencias:
    - H2 Database
#### Detalles
> Añadir estas Dependencias
> `mysql-connector-java`
```xml
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>8.0.28</version>
</dependency>
```
> `springdoc-openapi-starter-webmvc-ui`
```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.0.2</version>
</dependency>
```
> Código del YML
```yml
spring:
  application:
    name: ms-[nombre]-service
  profiles:
    active: development
  config:
    import: optional:configserver:http://root:123456@localhost:7070
```
> Agregar al Aplication.java
```java
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
```
> Estructura del Microservicios
```yml
config:
  OpenApiConfig.java
controller:
  Controller.java
dto:
  Dto.java
entity:
  .java
feign:
  [Nombre]Feign.java
repository:
  Repository.java
service:
  impl:
    ServiceImpl.java
  Service.java
util:
  Seeder.java

```
> `dto: Dto.java` es la información que se traerá del otro microservicio  
> Generalmente de esta forma se tiene de más en `entity: .java`
```java
  private Integer Id;

  @Transient
  private Dto Dto;
```
> `Feign.java` obtener los datos del otro microservicios
```java
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-[nombre]-service", path = "/[link]")
public interface [Nombre]Feign {
    @GetMapping("/{id}")
    @CircuitBreaker(name = "[example]ListarPorIdCB", fallbackMethod = "fallback[Example]ById")
    ResponseEntity<Dto> buscar[Example](@PathVariable Integer id);

    default ResponseEntity<Dto> fallback[Example]ById(Integer id, Exception e) {
        Dto Dto = new CategoriaDto();
        Dto.set[Dato]("Servicio no disponible de catálogo");
        return ResponseEntity.ok(Dto);
    }
}
```
> Config Data crear `ms-[nombre]-service.yml`
```yml
server:
  port: 8082

spring:
  application:
    name: ms-[nombre]-service

  datasource:
    url: jdbc:h2:mem:ms_[nombre]
    driver-class-name: org.h2.Driver
    username: sa
    password: password
    jpa:
      database-platform: org.hibernate.dialect.H2Dialect
    h2:
      console:
        enabled: true
        path: /h2-console

springdoc:
  api-docs:
    enabled: true
  swagger-ui:
    enabled: true
    path: /doc/swagger-ui.html

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8090/eureka
  instance:
    hostname: localhost

resilience4j.circuitbreaker:
  instances:
    [example]ListarPorIdCB:
      registerHealthIndicator: true
      slidingWindowSize: 10
      permittedNumberOfCallsInHalfOpenState: 3
      slidingWindowType: TIME_BASED
      minimumNumberOfCalls: 4
      waitDurationInOpenState: 5s
      failureRateThreshold: 50
      eventConsumerBufferSize: 10
```
> Subir al `Github` y correr el programa.
### Security Auth
#### Descripción
Autenticación y Seguridad
#### Creación
- Spring Boot
- Nombre: ms-auth-service
- Dependencias:
  - Spring Web
  - Config Client
  - Eureka Discovery Client
  - Security
  - Actuator
- Otras Dependencias:
  - Lombok
  - Spring Data JPA
- Más Dependencias:
  - H2 Database
#### Detalles
> Añadir estas Dependencias
> `jjwt`
```xml
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-api</artifactId>
  <version>0.12.6</version>
</dependency>
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-impl</artifactId>
  <version>0.12.6</version>
  <scope>runtime</scope>
</dependency>
  <dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-jackson</artifactId>
  <version>0.12.6</version>
  <scope>runtime</scope>
</dependency>
```
> `spring-cloud-starter-bootstrap`
```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-bootstrap</artifactId>
</dependency>
```
> `mysql-connector-java`
```xml
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>8.0.28</version>
</dependency>
```
> `springdoc-openapi-starter-webmvc-ui`
```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.0.2</version>
</dependency>
```
> Código del YML
```yml
spring:
  application:
    name: ms-auth-service
  profiles:
    active: development
  config:
    import: optional:configserver:http://root:123456@localhost:7070
```
> Estructura del Auth
```yml
config:
  OpenApiConfig.java
controller:
  AuthUserController.java
dto:
  AuthUserDto.java
  TokenDto.java
entity:
  AuthUser.java
repository:
  AuthUserRepository.java
security:
  JwtProvider.java
  PasswordEncoderConfig.java
  SecurityConfig.java
service:
  impl:
    AuthUserServiceImpl.java
  AuthUserService.java
```
> `repository: AuthUserRepository.java`
```java
Optional<AuthUser> findByUserName(String username);
```
> `security: JwtProvider.java`
```java
package com.example.msauthservice.security;

import com.example.msauthservice.entity.AuthUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }
    public String createToken(AuthUser authUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", authUser.getId());
        claims.put("sub", authUser.getUserName());

        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserNameFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return "bad token";
        }
    }
}
```
> `security: PasswordEncoderConfig.java`
```java
package com.example.msauthservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```
> `security: SecurityConfig.java`
```java
package com.example.msauthservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}
```
> `service: AuthUserService.java` aplica al implement
```java
  public AuthUser save(AuthUserDto authUserDto);
  public TokenDto login(AuthUserDto authUserDto);
  public TokenDto validate(String token);
```
> `controller: AuthUserController.java` Crear el Controller  
> Config Data crear `ms-auth-service.yml`
```yml
server:
  port: 8084

spring:
  application:
    name: ms-auth-service
  datasource:
    url: jdbc:h2:mem:ms_auth
    driver-class-name: org.h2.Driver
    username: sa
    password: password
    jpa:
      database-platform: org.hibernate.dialect.H2Dialect
    h2:
      console:
        enabled: true
        path: /h2-console

springdoc:
  api-docs:
    enabled: true
  swagger-ui:
    enabled: true
    path: /doc/swagger-ui.html

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8090/eureka
  instance:
    hostname: localhost

jwt:
  secret: clave_secreta_en_base_64_con_32_bits_porque_es_el_minimo
```
> Subir al `Github` y correr el programa.
### Load Balancer
#### Descripción
Balanceo de Carga, sirve para distribuir el trafico de red entre multiples servidores.
#### Desarrollo
> Cambiar a estas cosas en los diferentes archivos del `Config Data`
> `ms-auth-service.yml`
```yml
server:
  port: ${PORT:${SERVERS_PORT:0}}

eureka:
  client:
    serviceUrl:
      defaultZone: ${EUREKA_URI:http://localhost:8090/eureka}
  instance:
    instance-id: ${spring.application.name}:${spring.application.instance_id:${random.value}}
```
> `ms-[nombre]-service.yml`
```yml
server:
  port: ${PORT:${SERVERS_PORT:0}}

eureka:
  client:
    serviceUrl:
      defaultZone: ${EUREKA_URI:http://localhost:8090/eureka}
  instance:
    instance-id: ${spring.application.name}:${spring.application.instance_id:${random.value}}
```
> `ms-gateway-service.yml`
```yml
eureka:
  client:
    serviceUrl:
      defaultZone: ${EUREKA_URI:http://localhost:8090/eureka}
  instance:
    instance-id: ${spring.application.name}:${spring.application.instance_id:${random.value}}
```
> Se añade al `ms-gateway-service.yml` para saber que apis puede conectar
```yml
spring:
  cloud:
    gateway:
      discovery:
        locator:
          enable: true
      routes:
        - id: ms-[nombre]-service
          uri: lb://ms-[nombre]-service
          predicates:
            - Path=/[nombre]/**
```
> Subir al `Github` y correr el programa.

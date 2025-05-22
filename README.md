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
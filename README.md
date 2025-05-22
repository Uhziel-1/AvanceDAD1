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
>Agregar al Aplication.java
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

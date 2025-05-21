# AvanceDAD1
## Estructura de Archivos
- Config Data
- Config Server
- Registry
- Gateway
- Microservices
## Otros
- Intellij IDEA
- Spring Boot
- Java 17
- Maven
- Jar
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
- `aplication.properties` → `aplication.yml`
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
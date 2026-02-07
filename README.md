# Proyecto
Este proyecto es una aplicación de ejemplo que utiliza Java 21, Spring Boot y JPA para gestionar productos y franquicias. La aplicación sigue una arquitectura limpia, separando las capas de dominio, infraestructura y presentación.
Utilizando el scaffolding de bancolombia, implementando principalmente como servidor jetty, con una base de datos postgreSQL, utilizando docker como apoyo para la base de datos, jpa para la gestión de entidades y repositorios, y lombok para reducir el código boilerplate.

## Estructura del Proyecto
- `domain`: Contiene las entidades de negocio y las interfaces de los repositorios.
- `infrastructure`: Contiene las implementaciones de los repositorios utilizando JPA y la configuración de la base de datos.
- `presentation`: Contiene los controladores REST para exponer la API.

## Inicialización del Proyecto
1. Clona el repositorio:
   ```bash
   git clone
    ```
2. Navega al directorio del proyecto:
   ```bash
    cd prueba-seti-reactiva
    ```
3. En la capa de application, configura el archivo `application.properties` con los detalles de tu base de datos PostgreSQL.
4. Inicia la aplicación utilizando Gradle:
   ```bash
    ./gradlew bootRun
    ```

## Desarrollo
### Capa de Dominio
- En esta capa se definen las entidades `Franquicia`, `Sucursal` y `Producto`, así como las interfaces de los repositorios para cada una de estas entidades, que actuan como entry points para la capa de infraestructura.
### Capa de Infraestructura
- Aquí se implementan los repositorios utilizando JPA, definiendo las entidades correspondientes a las tablas de la base de datos y las interfaces que extienden `JpaRepository` para cada una de las entidades. Usando un adapter para mapear entre los modelos de dominio y los modelos de persistencia.
### Capa de Aplicación
- En esta capa se definen los servicios que contienen la lógica de negocio y los controladores REST para exponer la API. Los controladores manejan las solicitudes HTTP y delegan la lógica de negocio a los servicios, que a su vez interactúan con los repositorios para acceder a los datos.

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
3. En la capa de application, configura el archivo `application.properties` o `application.yml` con los detalles de tu base de datos PostgreSQL.  especificamente las variables:
    - `adapter.r2dbc.url`
    - `adapter.r2dbc.username`
    - `adapter.r2dbc.password`

4. Asegúrate de tener PostgreSQL en ejecución y la base de datos configurada según las necesidades del proyecto, puedes usar las sentencias DDL que se encuentran en la carpeta `database` para crear las tablas necesarias.

4. Inicia la aplicación utilizando Gradle:
   ```bash
    ./gradlew bootRun
    ```
## Entidades
- `Franquicia`: Representa una franquicia con atributos como `id`, `nombre` y `descripcion`.
- `Sucursal`: Representa una sucursal de una franquicia, con atributos como `id`, `nombre`, y una referencia a la `Franquicia` a la que pertenece.
- `Producto`: Representa un producto con atributos como `id`, `nombre`, `stock` y una referencia a la `Sucursal` donde se encuentra.


## Desarrollo
### Capa de Dominio
- En esta capa se definen las entidades `Franquicia`, `Sucursal` y `Producto`, así como las interfaces de los repositorios para cada una de estas entidades, que actuan como entry points para la capa de infraestructura.
- Se implementan los diferentes casos de uso (use cases) que contienen la lógica de negocio para manejar las operaciones relacionadas con las entidades, como obtener todas las franquicias, obtener una franquicia por ID, crear una nueva franquicia, etc. 
- Se implementa un modelo agregado para `Franqucia` que incluye una lista de `Sucursal`. Esto permite manejar la relación entre franquicias y sucursales de manera más eficiente y coherente dentro del dominio, facilitando la gestión de las operaciones relacionadas con ambas entidades.
### Capa de Infraestructura
- Aquí se implementan los repositorios utilizando R2DBC, definiendo las entidades correspondientes a las tablas de la base de datos y las interfaces que extienden `ReactiveRepository` para cada una de las entidades. Usando un adapter para mapear entre los modelos de dominio y los modelos de persistencia.
- Usamos ReactiveWeb como entry point porque el dominio y la infraestructura son reactivos; MVC introduciría bloqueo y rompería el modelo de backpressure(backpressure es la capacidad de un sistema para manejar la presión de retroceso en flujos de datos reactivos).
- En el driver adapter, se implementan los métodos para mapear entre los modelos de dominio y los modelos de persistencia, lo que permite una separación clara entre las capas y facilita el mantenimiento del código, debido a que el mapper generico no pudo ser implementado por limitaciones del framework y del lenguaje.
- En esta capa especificamente en los controllers, aplicamos los operadores reactivos como `doOnNext`, `doOnError` y `doOnComplete` para agregar logging y manejo de errores en las operaciones de los repositorios, lo que mejora la trazabilidad y facilita la depuración de la aplicación.


### Capa de Aplicación
- En esta capa se definen los servicios que contienen la lógica de negocio y los controladores REST para exponer la API. Los controladores manejan las solicitudes HTTP y delegan la lógica de negocio a los servicios, que a su vez interactúan con los repositorios para acceder a los datos.

## Prerequisito
- Java 21
- PostgreSQL, donde se creará una base de datos para la aplicación. Puedes usar Docker para facilitar la configuración de PostgreSQL.
- Gradle para gestionar las dependencias y ejecutar la aplicación.
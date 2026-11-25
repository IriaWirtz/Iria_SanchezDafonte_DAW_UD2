# Aplicación TODO

Gestor de tareas con **Spring Boot 3.5**, **Java 21** y **MariaDB**. Permite consultar, crear, completar (y reabrir) y eliminar tareas desde el navegador.

> No hay que programar nada: el objetivo de la práctica es **construir la imagen Docker, publicarla y desplegarla**.

## Estructura (arquitectura por capas)

```
src/main/java/com/dwes/todo
├── TodoApplication.java          punto de entrada
├── controller/TareaController    peticiones web y vistas (Thymeleaf)
├── service/TareaService          lógica de negocio (interfaz)
│   └── TareaServiceImpl          implementación, con transacciones
├── repository/TareaRepository    acceso a datos (Spring Data JPA)
├── entity/Tarea                  entidad JPA → tabla "tareas"
├── dto/TareaDto                  datos que se muestran en la vista
│   └── TareaCrearDto             datos del formulario, con validación
├── mapper/TareaMapper            convierte entre entidad y DTO
└── exception/                    excepciones propias y @ControllerAdvice
src/main/resources
├── application.properties        configuración (variables de entorno)
├── templates/                    vistas Thymeleaf
└── static/css/                   estilos
```

Flujo de una petición: `Controller → Service → Repository → MariaDB`.

## Configuración de la base de datos

La aplicación **no lleva credenciales dentro del código**. Se leen de variables de entorno (ver `src/main/resources/application.properties`):

| Variable | Obligatoria | Descripción |
|---|---|---|
| `DB_HOST` | Sí | Servidor de MariaDB (nombre del contenedor o del servicio) |
| `DB_PORT` | No (3306) | Puerto de MariaDB |
| `DB_NAME` | Sí | Nombre de la base de datos |
| `DB_USER` | Sí | Usuario de la base de datos |
| `DB_PASSWORD` | Sí | Contraseña del usuario |

Si falta alguna variable obligatoria, la aplicación no arranca y el error indica cuál (`Could not resolve placeholder 'DB_NAME'`). La aplicación necesita que MariaDB esté **ya en marcha** al arrancar; la tabla `tareas` se crea sola la primera vez.

## Datos técnicos para construir la imagen

- Proyecto Maven: `pom.xml` en la raíz. Comando de compilación: `mvn package -DskipTests`.
- El JAR resultante se llama siempre **`target/todo-app.jar`**.
- Se ejecuta con `java -jar` (Java 21) y escucha en el puerto **8080** dentro del contenedor.
- Imágenes base sugeridas: una con **Maven y JDK 21** para compilar y una con **JRE 21** para ejecutar.

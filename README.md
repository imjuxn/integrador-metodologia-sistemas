# Sistema de Gestión de Solicitudes Académicas

Prototipo de consola del Trabajo Práctico Integrador de Metodología de Sistemas II.
Este README describe el código de `integracion` revisado en el commit
[`76c0ece`](https://github.com/imjuxn/integrador-metodologia-sistemas/commit/76c0ecebadd2efa278397ef09eedaf9328b200d2), posterior a R-01–R-04.

## Alcance implementado

- Registrar solicitudes con número correlativo por instancia del servicio y estado inicial `ABIERTA`.
- Asignar un responsable, cambiar el estado y consultar solicitudes por estado.
- Generar un reporte de texto: número, tipo y estado, en orden de registro.
- Registrar mediante `DatosSolicitud` o la firma original de cuatro parámetros, que delega en aquella.

Los datos se guardan en un `LinkedHashMap` dentro de `SolicitudService` y se pierden al terminar la ejecución.
`App` ejecuta un ejemplo fijo: no ofrece un menú interactivo. No hay interfaz web, autenticación, cuentas, permisos ni base de datos.

El registro valida estudiante, legajo, nombre, correo (solo presencia de `@`), tipo, detalle y prioridad.
Las prioridades aceptadas son exactamente `ALTA`, `MEDIA` y `BAJA`; solo tipo y detalle se recortan con `trim()`.
Los estados son `ABIERTA`, `EN_PROCESO` y `CERRADA`: no se controlan transiciones entre ellos.
El servicio rechaza responsables y estados nulos, y números inexistentes al asignar o cambiar estado.
Los records no incorporan validaciones propias y los setters públicos de `Solicitud` permiten modificarla directamente.

## Requisitos

- Git para clonar el repositorio.
- JDK 17 o superior, con `java` y `javac` disponibles. La compilación usa `release 17`.
- Acceso a Internet para descargar Maven y dependencias en la primera ejecución; permisos de escritura en la caché del usuario.
- Windows con PowerShell para `mvnw.cmd`, o Linux/macOS con shell POSIX para `mvnw`.

Se incluye Maven Wrapper 3.3.4, configurado para Maven 3.10.0 en
[`.mvn/wrapper/maven-wrapper.properties`](.mvn/wrapper/maven-wrapper.properties).
No hace falta instalar Maven por separado. El [`pom.xml`](pom.xml) declara JUnit Jupiter 6.1.3,
Compiler Plugin 3.14.1 y Surefire 3.5.5. [JUnit 6 requiere Java 17 o superior](https://junit.org/).

## Instalación, pruebas y ejecución

Ejecutar los comandos desde la raíz del repositorio. Las instrucciones seleccionan explícitamente `integracion`.

### Windows (PowerShell)

```powershell
git clone --branch integracion https://github.com/imjuxn/integrador-metodologia-sistemas.git
cd integrador-metodologia-sistemas
java -version
javac -version
.\mvnw.cmd --version
.\mvnw.cmd test
.\mvnw.cmd package
java -cp target/classes ar.edu.metodologia.App
```

### Linux/macOS

```sh
git clone --branch integracion https://github.com/imjuxn/integrador-metodologia-sistemas.git
cd integrador-metodologia-sistemas
java -version
javac -version
chmod +x mvnw
./mvnw --version
./mvnw test
./mvnw package
java -cp target/classes ar.edu.metodologia.App
```

El archivo `mvnw` está versionado con modo `100644`; por eso se incluye `chmod +x`.
También se puede usar `sh ./mvnw test` y `sh ./mvnw package`.
`package` compila, ejecuta las pruebas y genera el JAR; el ejemplo se inicia con el classpath indicado, sin depender de un manifiesto ejecutable.

Salida del ejemplo:

```text
Solicitudes en proceso: 1
1 | CERTIFICADO | EN_PROCESO
```

Si no se encuentra Java, revisar el JDK, `JAVA_HOME` y `PATH`.
Si Maven no descarga dependencias, revisar la conectividad y el proxy.
Si informa un error de bloqueo de caché, revisar los permisos de `.m2` y otras ejecuciones de Maven.

## Pruebas y nivel de verificación

En [`SolicitudServiceTest.java`](src/test/java/ar/edu/metodologia/SolicitudServiceTest.java)
se comprobaron **11 métodos anotados con `@Test`**:

| Método | Escenario comprobado |
| --- | --- |
| `registraSolicitudConNumeroYEstadoInicial` | Número inicial 1 y estado ABIERTA |
| `rechazaDatosInvalidosSinGuardarSolicitud` | Tipo en blanco, sin guardar |
| `asignaResponsable` | Asignación del área del responsable |
| `cambiaEstadoYPermiteFiltrar` | Cambio a EN_PROCESO y filtrado |
| `generaReporteSimple` | Texto del reporte de una solicitud |
| `rechazaEstudianteNulo` | Rechazo de estudiante nulo |
| `rechazaCorreoInvalido` | Correo sin arroba |
| `rechazaPrioridadInvalida` | Rechazo de URGENTE |
| `registrarConDatosSolicitud` | Registro mediante el nuevo record |
| `registrarConDatosSolicitudEliminaEspacios` | Recorte de tipo y detalle |
| `registrarConDatosSolicitudNulos` | Rechazo del objeto de parámetros nulo |

**Verificación nueva, 08/11/2026:** sobre el código de `76c0ece`, en Windows con JDK 21.0.8,
se ejecutó `.\mvnw.cmd test`: **11 pruebas, 0 fallos, 0 errores, 0 omitidas; BUILD SUCCESS**.
También se ejecutó `java -cp target/classes ar.edu.metodologia.App` y se obtuvo la salida mostrada.
El primer intento de pruebas se interrumpió por permisos de acceso al bloqueo de la caché Maven; la repetición con acceso autorizado terminó correctamente.
Los informes de cada ejecución local quedan en `target/surefire-reports/` y no se versionan.

**Límites:** los comandos Unix fueron contrastados con los scripts y las rutas del repositorio, pero no ejecutados en Linux/macOS en esta revisión.
No se ejecutó `package` en esta revisión. Los resultados anteriores registrados por los integrantes son evidencia histórica, distinta de esta ejecución.
Las pruebas no cubren todas las validaciones ni garantizan ausencia de errores: faltan, entre otros casos, aceptación de ALTA y BAJA,
reporte vacío y pruebas aisladas de `ReporteSolicitudes`.

## Refactorings implementados

| ID | Técnica | Resultado real | Trazabilidad |
| --- | --- | --- | --- |
| R-01 | Extract Method | Validaciones separadas en `validarRegistro(Estudiante, String, String, String)` | [742237a](https://github.com/imjuxn/integrador-metodologia-sistemas/commit/742237a) |
| R-02 | Move Method | Formato del reporte en `ReporteSolicitudes.generar(Collection<Solicitud>)`; `generarReporte()` delega | [7d00434](https://github.com/imjuxn/integrador-metodologia-sistemas/commit/7d00434), [PR #2](https://github.com/imjuxn/integrador-metodologia-sistemas/pull/2) |
| R-03 | Introduce Parameter Object | Record `DatosSolicitud` y sobrecarga de `registrar`; se conserva la firma original | [77af974](https://github.com/imjuxn/integrador-metodologia-sistemas/commit/77af974), [integración 7eeadf2](https://github.com/imjuxn/integrador-metodologia-sistemas/commit/7eeadf2), [PR #1](https://github.com/imjuxn/integrador-metodologia-sistemas/pull/1) |
| R-04 | Extract Method | Regla de prioridad en `validarPrioridad(String)`, llamada desde `validarRegistro` tras integrar | [31aded9](https://github.com/imjuxn/integrador-metodologia-sistemas/commit/31aded9), [integración 18e8458](https://github.com/imjuxn/integrador-metodologia-sistemas/commit/18e8458) |

Estos cambios reorganizan el código conservando las operaciones existentes. El objeto de parámetros incorpora además el rechazo explícito de `DatosSolicitud` nulo.

## Arquitectura

El [diagrama de clases y sus límites](docs/arquitectura.md) describe los ocho tipos de producción existentes después de R-01–R-04.

## Estructura del proyecto

```text
.
├── README.md
├── pom.xml
├── mvnw / mvnw.cmd
├── .mvn/wrapper/maven-wrapper.properties
├── src/main/java/ar/edu/metodologia/
│   ├── App.java
│   ├── DatosSolicitud.java
│   ├── Estado.java
│   ├── Estudiante.java
│   ├── ReporteSolicitudes.java
│   ├── Responsable.java
│   ├── Solicitud.java
│   └── SolicitudService.java
├── src/test/java/ar/edu/metodologia/SolicitudServiceTest.java
└── docs/
    ├── arquitectura.md
    ├── integrante1.txt
    ├── integrante2.md
    ├── integrante3.md
    ├── integrante4.md
    └── evidencias/integrante2/
```

`target/` se genera al compilar y contiene las clases, los informes de pruebas y, después de `package`, el JAR.

## Documentación de los cuatro integrantes

- [Integrante 1: sistema base, diagnóstico y R-01](docs/integrante1.txt).
- [Integrante 2, Nicolas Demiryi: hallazgos y R-02](docs/integrante2.md); [capturas](docs/evidencias/integrante2/).
- [Integrante 3, Ezequiel Alejandro Ventura: R-03 e integración](docs/integrante3.md).
- [Integrante 4, Gabriel Valdez Arce: CS-07, CS-08, R-04, pruebas y revisión](docs/integrante4.md).

Los documentos individuales conservan su contexto histórico: las menciones de PR pendiente o Draft en integrante2 e integrante3
no representan el estado actual. Al revisar GitHub el 08/11/2026, **PR #1 y PR #2 estaban fusionados hacia `integracion`**,
y no había un PR abierto de `integracion` hacia `main`. R-01 y R-04 se trazan por commits, sin atribuirles PR inexistentes.
`main` seguía en `07b71a2`; `integracion` incluía la documentación nueva del integrante 4 en `76c0ece`.

## Seis tags existentes

| Tag | Commit al que resuelve |
| --- | --- |
| [estado-inicial](https://github.com/imjuxn/integrador-metodologia-sistemas/tree/estado-inicial) | `07b71a2` |
| [estadorefactorizado](https://github.com/imjuxn/integrador-metodologia-sistemas/tree/estadorefactorizado) | `a7f33c8` |
| [etapa-diagnostico](https://github.com/imjuxn/integrador-metodologia-sistemas/tree/etapa-diagnostico) | `a7f33c8` |
| [etapa-docs](https://github.com/imjuxn/integrador-metodologia-sistemas/tree/etapa-docs) | `a7f33c8` |
| [etapa-pruebas](https://github.com/imjuxn/integrador-metodologia-sistemas/tree/etapa-pruebas) | `a7f33c8` |
| [etapa-refactoring](https://github.com/imjuxn/integrador-metodologia-sistemas/tree/etapa-refactoring) | `a7f33c8` |

Los cinco tags posteriores al inicial son anotados y apuntan al mismo commit consolidado:
sus nombres no prueban cinco estados de código diferentes. No incluyen el commit posterior `76c0ece` ni esta actualización del README.
Esta documentación no mueve ni recrea tags.

## Propuestas y deuda técnica: no implementadas

- Abstraer el almacenamiento mediante `SolicitudRepository` (CS-06). **No existe esa clase ni interfaz en el código actual.**
- Inyectar `ReporteSolicitudes` por constructor para reducir acoplamiento.
- Mover validaciones de datos personales a `Estudiante` (CS-03) y reducir validaciones repetidas de texto (CS-04).
- Reemplazar las prioridades de texto por un `enum Prioridad` (CS-07).
- Definir reglas de transición y limitar los setters públicos de estado (CS-08).
- Ampliar las pruebas según las limitaciones señaladas.

Estas propuestas no se representan como clases implementadas en el diagrama.

# Arquitectura del sistema

Describe el código de `integracion` revisado en `76c0ece`, posterior a R-01–R-04.

## Diagrama de clases simplificado

Representa únicamente los ocho tipos de producción existentes. Omite constructores, accesores y algunos miembros para facilitar la lectura.
Las flechas discontinuas indican uso; las continuas, referencias almacenadas. No expresan reglas de negocio adicionales.

```mermaid
classDiagram
    class App {
        +main(String[] args) void
    }
    class SolicitudService {
        +registrar(DatosSolicitud datos) Solicitud
        +registrar(Estudiante estudiante, String tipo, String detalle, String prioridad) Solicitud
        +asignarResponsable(int numero, Responsable responsable) void
        +cambiarEstado(int numero, Estado estado) void
        +listarPorEstado(Estado estado) List~Solicitud~
        +generarReporte() String
        -validarRegistro(Estudiante estudiante, String tipo, String detalle, String prioridad) void
        -validarPrioridad(String prioridad) void
        -obtener(int numero) Solicitud
    }
    class DatosSolicitud {
        <<record>>
        Estudiante estudiante
        String tipo
        String detalle
        String prioridad
    }
    class Solicitud {
        +getNumero() int
        +getTipo() String
        +getEstado() Estado
        +setEstado(Estado estado) void
        +setResponsable(Responsable responsable) void
    }
    class Estudiante {
        <<record>>
        String legajo
        String nombre
        String correo
    }
    class Responsable {
        <<record>>
        String nombre
        String area
    }
    class Estado {
        <<enumeration>>
        ABIERTA
        EN_PROCESO
        CERRADA
    }
    class ReporteSolicitudes {
        +generar(Collection~Solicitud~ solicitudes) String
    }
    App ..> SolicitudService : usa
    App ..> Estudiante : crea
    App ..> Responsable : crea
    App ..> Estado : usa
    App ..> Solicitud : usa
    SolicitudService --> Solicitud : almacena en memoria
    SolicitudService --> ReporteSolicitudes : delega reporte
    SolicitudService ..> DatosSolicitud : recibe y crea
    SolicitudService ..> Responsable : asigna
    SolicitudService ..> Estado : filtra y cambia
    DatosSolicitud --> Estudiante : contiene
    Solicitud --> Estudiante : referencia
    Solicitud --> Responsable : referencia opcional
    Solicitud --> Estado : estado actual
    ReporteSolicitudes ..> Solicitud : consulta
```

## Alcance y límites

`SolicitudService` conserva las solicitudes en memoria y delega el formato del reporte en `ReporteSolicitudes`.
`DatosSolicitud`, `Estudiante` y `Responsable` son records; `Estado` es una enumeración.

El diagrama representa la implementación existente. `SolicitudRepository`, un `enum Prioridad`,
la inyección del reporte por constructor y las reglas de transición de estados siguen como propuestas
no implementadas.
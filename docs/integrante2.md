# R-02 — Move Method

**Autor:** Nicolas Demiryi  
**Proyecto:** Sistema de Gestión de Solicitudes Académicas  
**Hallazgos asociados:** CS-02 (resuelto con R-02), CS-03 y CS-04 (documentados)  
**Rama:** `integrante2/move-method-reporte`

## 1. Diagnóstico

En la versión inicial, `SolicitudService` registraba, asignaba, cambiaba estados y consultaba solicitudes, y además armaba el texto del reporte dentro de `generarReporte()`. Cambiar el formato del reporte obligaba a modificar la clase que maneja las reglas de las solicitudes (CS-02).

Además documenté dos hallazgos propios:

**CS-03 – Feature Envy:** `registrar()` valida `legajo()`, `nombre()` y `correo()`, que son datos de `Estudiante`. `Estudiante` es un record sin validaciones propias. Ubicación: `SolicitudService.java`, líneas 16 a 24, y `Estudiante.java`, líneas 3 a 4 de `estado-inicial`. Prioridad media.

**CS-04 – Duplicate Code:** el bloque `if (valor == null || valor.isBlank()) throw ...` se repite cuatro veces para legajo, nombre, tipo y detalle. Ubicación: `SolicitudService.java`, líneas 16 a 30 de `estado-inicial`. Prioridad baja.

También consolidé los ocho hallazgos del equipo en la tabla de priorización de la sección 4.9 del informe.

## 2. Refactoring implementado

Se creó la clase `ReporteSolicitudes` con el método `generar(Collection<Solicitud>)` y se movió ahí el armado del texto del reporte.

En `SolicitudService` se agregó el atributo `reporte` y se conservó `generarReporte()`, que ahora delega en la nueva clase. Así `App` y las pruebas siguen llamando al mismo método y el comportamiento externo no cambia.

El cambio se hizo sobre la rama `integracion`, que ya incluía R-01, R-03 y R-04. Como solo modifica `generarReporte()`, no generó conflictos con los cambios anteriores.

## 3. Principios y buenas prácticas

**SRP:** `SolicitudService` gestiona las solicitudes y `ReporteSolicitudes` se encarga de presentarlas. El formato del reporte se modifica en una sola clase.

**Cohesión:** el servicio queda enfocado en las operaciones sobre solicitudes.

**DIP:** `ReporteSolicitudes` se crea dentro del servicio. Recibirla por constructor quedó como mejora futura, junto con CS-06.

## 4. Pruebas y verificación

Se ejecutó `./mvnw clean test` mediante Maven y JUnit antes y después del cambio.

**Resultado antes:** 11 pruebas exitosas, 0 fallos, 0 errores y `BUILD SUCCESS`.  
**Resultado después:** 11 pruebas exitosas, 0 fallos, 0 errores y `BUILD SUCCESS`, incluida `generaReporteSimple`, que verifica el reporte.

La App mostró la misma salida en las dos versiones: `Solicitudes en proceso: 1` y `1 | CERTIFICADO | EN_PROCESO`.

Los resultados respaldan los escenarios evaluados, sin garantizar la ausencia absoluta de regresiones.

## 5. Trazabilidad

- Repositorio: https://github.com/imjuxn/integrador-metodologia-sistemas
- Evidencias y hallazgos: https://github.com/imjuxn/integrador-metodologia-sistemas/commit/7777e5b
- Refactoring: https://github.com/imjuxn/integrador-metodologia-sistemas/commit/7d00434
- Pull Request: https://github.com/imjuxn/integrador-metodologia-sistemas/pull/2

Las capturas están en `docs/evidencias/integrante2/` y también incorporadas al informe colaborativo.

## 6. Pendientes

El Pull Request #2 está pendiente de revisión y aprobación. CS-03 y CS-04 quedan como propuestas no implementadas: mover las validaciones a `Estudiante` y extraer un método auxiliar `requerirTexto`. Una prueba propia para `ReporteSolicitudes` queda como mejora.

## 7. Uso de inteligencia artificial

Se utilizó Claude como apoyo para aprender a usar Git y GitHub, ubicar las líneas de los hallazgos, proponer el código de R-02 y redactar documentación. La aplicación de los cambios, las pruebas locales y la revisión de los textos fueron realizadas por el estudiante.
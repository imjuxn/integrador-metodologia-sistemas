# R-03 — Introduce Parameter Object

**Autor:** Ezequiel Alejandro Ventura  
**Proyecto:** Sistema de Gestión de Solicitudes Académicas  
**Hallazgo asociado:** CS-05  
**Rama:** `integrante3/solid-refactoring`

## 1. Diagnóstico

En la versión inicial, el método `SolicitudService.registrar()` recibía cuatro parámetros individuales: `Estudiante`, `tipo`, `detalle` y `prioridad`.

Aunque cuatro parámetros no representan necesariamente una lista excesivamente larga, los datos están relacionados con una misma operación. Además, los parámetros de tipo `String` pueden confundirse al realizar una llamada.

Por ello, se identificó una oportunidad de mejora de legibilidad mediante el refactoring **Introduce Parameter Object**.

## 2. Refactoring implementado

Se creó el `record DatosSolicitud` para agrupar los datos necesarios para registrar una solicitud.

Se incorporó `registrar(DatosSolicitud)` como implementación principal y se conservó la firma original por compatibilidad.

Durante la integración se incorporó el método `validarRegistro()`, desarrollado por otro integrante mediante Extract Method. Se resolvió manualmente un conflicto de merge, conservando las validaciones y el comportamiento existente.

## 3. Principios y buenas prácticas

**Clean Code:** el objeto `DatosSolicitud` expresa mejor el significado de los parámetros y mejora la legibilidad del método.

**DRY:** las dos firmas de `registrar()` comparten una única implementación y las mismas validaciones.

**SRP:** la separación de validaciones mejora la organización interna. Sin embargo, el uso de `DatosSolicitud` no demuestra por sí solo el cumplimiento general de SRP.

**DIP:** se identificó como mejora futura la posibilidad de abstraer el almacenamiento mediante `SolicitudRepository` (CS-06). Esta mejora no fue implementada.

## 4. Pruebas y verificación

Se ejecutó `.\mvnw.cmd test` mediante Maven y JUnit.

**Resultado:** 11 pruebas exitosas, 0 fallos, 0 errores y `BUILD SUCCESS`.

Las tres pruebas agregadas verifican el registro mediante `DatosSolicitud`, la eliminación de espacios con `trim()` y el tratamiento de un objeto nulo.

Los resultados respaldan los escenarios evaluados, sin garantizar la ausencia absoluta de regresiones.

## 5. Trazabilidad

- Repositorio: https://github.com/imjuxn/integrador-metodologia-sistemas
- Refactoring: https://github.com/imjuxn/integrador-metodologia-sistemas/commit/77af974
- Integración: https://github.com/imjuxn/integrador-metodologia-sistemas/commit/7eeadf2
- Pull Request: https://github.com/imjuxn/integrador-metodologia-sistemas/pull/1

Las capturas del código anterior y posterior y la evidencia de las pruebas están incorporadas al informe colaborativo.

## 6. Pendientes

El Pull Request permanece en estado Draft, pendiente de revisión. La integración definitiva y la creación de los tags restantes deben coordinarse con el equipo.

## 7. Uso de inteligencia artificial

Se utilizó ChatGPT como apoyo para analizar alternativas de diseño, orientar el refactoring, diseñar pruebas y redactar documentación. La implementación, las pruebas locales y la resolución del conflicto fueron realizadas por el estudiante.
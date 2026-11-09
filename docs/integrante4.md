# Integrante 4 — Gabriel Valdez Arce

## Aporte
- Hallazgos CS-07 y CS-08 (sección 4 del informe).
- Refactoring R-04: Extract Method de la regla de prioridad.
- Pruebas y verificación de la rama integrada (secciones 7.1 a 7.3).
- Checklist de code review y revisiones (sección 8).

## Hallazgos
- **CS-07 — Primitive Obsession y Magic Strings en la prioridad.** `SolicitudService.registrar`, líneas 25 a 33 de estado-inicial. La prioridad se compara contra "ALTA", "MEDIA" y "BAJA" escritos dentro del `if`, y el tipo es texto libre. Prioridad: media. Acción: R-04 aísla la regla; el `enum Prioridad` queda como propuesta no implementada.
- **CS-08 — Estado sin reglas de transición y setters expuestos.** `Solicitud.java` (líneas 28 y 29) y `SolicitudService.cambiarEstado` (líneas 46 a 51). Una solicitud puede pasar de CERRADA a ABIERTA sin error. Prioridad: media. Acción: propuesta no implementada.

## Refactoring R-04
- Técnica: Extract Method. Commit: `31aded9`, rama `integrante4/refactoring-r04`.
- Cambio: el `if` de la prioridad se movió de `registrar` al método privado `validarPrioridad`. No cambian las reglas ni el mensaje de error.
- Integración: commit `18e8458` en la rama `integracion`. Se resolvió un conflicto en `SolicitudService.java` dejando la llamada a `validarPrioridad` dentro de `validarRegistro`.

## Verificación
- Rama con R-04: 8 pruebas, 0 fallos, BUILD SUCCESS (02/11/2026).
- Rama integrada (R-01 + R-03 + R-04): 11 pruebas, 0 fallos, BUILD SUCCESS (03/11/2026).
- La salida de `App` coincide con la de la versión inicial.
- Límite: ninguna prueba comprueba que ALTA y BAJA se acepten; solo se prueba que "URGENTE" se rechace.

## Checklist de code review (aplicado a la rama integrada)
| Criterio | Resultado |
|---|---|
| Nombres expresivos y consistentes | Cumple |
| Funciones acotadas | Cumple |
| Responsabilidades claras | Parcial (el servicio todavía administra el almacenamiento, CS-06) |
| Ausencia de duplicación evitable | Parcial (validaciones de texto repetidas, CS-04) |
| Errores y validaciones adecuadas | Parcial (el correo solo se valida con "@"; el estado no valida transiciones, CS-08) |
| Acoplamiento controlado | Parcial (LinkedHashMap concreto; setters públicos) |
| Pruebas pertinentes | Parcial (la prioridad solo se prueba con URGENTE) |
| Commits y documentación claros | Parcial |
| Sin datos sensibles expuestos | Cumple |

## Revisiones
- **Revisión de R-01** (commit `742237a`), por Gabriel Valdez. Observación 2.1 (criticidad baja): no se agregaron pruebas para legajo, nombre ni detalle en blanco. Decisión: aprobado. Registrada como comentario en GitHub.
- **Revisión de R-04** (commits `31aded9` y `18e8458`). Observación 3.1 (criticidad baja): se conservan los textos fijos y las pruebas solo cubren URGENTE. Revisor y decisión: pendientes de registrar.

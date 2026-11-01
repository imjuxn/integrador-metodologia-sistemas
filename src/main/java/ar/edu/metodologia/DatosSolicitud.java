package ar.edu.metodologia;

public record DatosSolicitud(
    Estudiante estudiante,
    String tipo,
    String detalle,
    String prioridad
) {}
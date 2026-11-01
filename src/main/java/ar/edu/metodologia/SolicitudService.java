package ar.edu.metodologia;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SolicitudService {
    private final Map<Integer, Solicitud> solicitudes = new LinkedHashMap<>();
    private int siguienteNumero = 1;

public Solicitud registrar(DatosSolicitud datos) {
    if (datos == null) {
        throw new IllegalArgumentException(
            "Los datos de la solicitud son obligatorios"
        );
    }

    Estudiante estudiante = datos.estudiante();
    String tipo = datos.tipo();
    String detalle = datos.detalle();
    String prioridad = datos.prioridad();

    validarRegistro(estudiante, tipo, detalle, prioridad);

    Solicitud solicitud = new Solicitud(
        siguienteNumero++,
        estudiante,
        tipo.trim(),
        detalle.trim(),
        prioridad
    );

    solicitudes.put(solicitud.getNumero(), solicitud);
    return solicitud;
}

public Solicitud registrar(
        Estudiante estudiante,
        String tipo,
        String detalle,
        String prioridad) {

    return registrar(new DatosSolicitud(
        estudiante,
        tipo,
        detalle,
        prioridad
    ));
}

private void validarRegistro(
        Estudiante estudiante,
        String tipo,
        String detalle,
        String prioridad) {

        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }
        if (estudiante.legajo() == null || estudiante.legajo().isBlank()) {
            throw new IllegalArgumentException("El legajo es obligatorio");
        }
        if (estudiante.nombre() == null || estudiante.nombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (estudiante.correo() == null || !estudiante.correo().contains("@")) {
            throw new IllegalArgumentException("El correo no es valido");
        }
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo es obligatorio");
        }
        if (detalle == null || detalle.isBlank()) {
            throw new IllegalArgumentException("El detalle es obligatorio");
        }
        if (!"ALTA".equals(prioridad)
                && !"MEDIA".equals(prioridad)
                && !"BAJA".equals(prioridad)) {
            throw new IllegalArgumentException(
                "La prioridad debe ser ALTA, MEDIA o BAJA"
            );
        }
    }

    public void asignarResponsable(int numero, Responsable responsable) {
        if (responsable == null) {
            throw new IllegalArgumentException("El responsable es obligatorio");
        }
        obtener(numero).setResponsable(responsable);
    }

    public void cambiarEstado(int numero, Estado estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado es obligatorio");
        }
        obtener(numero).setEstado(estado);
    }

    public List<Solicitud> listarPorEstado(Estado estado) {
        List<Solicitud> resultado = new ArrayList<>();
        for (Solicitud solicitud : solicitudes.values()) {
            if (solicitud.getEstado() == estado) {
                resultado.add(solicitud);
            }
        }
        return resultado;
    }

    public String generarReporte() {
        StringBuilder reporte = new StringBuilder();
        for (Solicitud solicitud : solicitudes.values()) {
            reporte.append(solicitud.getNumero()).append(" | ")
                   .append(solicitud.getTipo()).append(" | ")
                   .append(solicitud.getEstado()).append("\n");
        }
        return reporte.toString();
    }

    private Solicitud obtener(int numero) {
        Solicitud solicitud = solicitudes.get(numero);
        if (solicitud == null) {
            throw new IllegalArgumentException("No existe la solicitud " + numero);
        }
        return solicitud;
    }
}

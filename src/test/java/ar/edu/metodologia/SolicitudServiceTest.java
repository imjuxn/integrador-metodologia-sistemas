package ar.edu.metodologia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class SolicitudServiceTest {
    private final Estudiante estudiante = new Estudiante("12345", "Ana Perez", "ana@example.com");

    @Test
    void registraSolicitudConNumeroYEstadoInicial() {
        Solicitud solicitud = new SolicitudService().registrar(estudiante, "CERTIFICADO", "Alumno regular", "MEDIA");
        assertEquals(1, solicitud.getNumero());
        assertEquals(Estado.ABIERTA, solicitud.getEstado());
    }

    @Test
    void rechazaDatosInvalidosSinGuardarSolicitud() {
        SolicitudService servicio = new SolicitudService();
        assertThrows(IllegalArgumentException.class, () -> servicio.registrar(estudiante, " ", "Alumno regular", "MEDIA"));
        assertEquals(0, servicio.listarPorEstado(Estado.ABIERTA).size());
    }

    @Test
    void asignaResponsable() {
        SolicitudService servicio = new SolicitudService();
        Solicitud solicitud = servicio.registrar(estudiante, "CERTIFICADO", "Alumno regular", "MEDIA");
        servicio.asignarResponsable(solicitud.getNumero(), new Responsable("Luis", "Alumnado"));
        assertEquals("Alumnado", solicitud.getResponsable().area());
    }

    @Test
    void cambiaEstadoYPermiteFiltrar() {
        SolicitudService servicio = new SolicitudService();
        Solicitud solicitud = servicio.registrar(estudiante, "CERTIFICADO", "Alumno regular", "MEDIA");
        servicio.cambiarEstado(solicitud.getNumero(), Estado.EN_PROCESO);
        assertEquals(1, servicio.listarPorEstado(Estado.EN_PROCESO).size());
        assertEquals(0, servicio.listarPorEstado(Estado.ABIERTA).size());
    }

    @Test
    void generaReporteSimple() {
        SolicitudService servicio = new SolicitudService();
        servicio.registrar(estudiante, "CERTIFICADO", "Alumno regular", "MEDIA");
        assertEquals("1 | CERTIFICADO | ABIERTA\n", servicio.generarReporte());
    }
    @Test
    void rechazaEstudianteNulo() {
        SolicitudService servicio = new SolicitudService();
        assertThrows(IllegalArgumentException.class, () -> servicio.registrar(null, "CERTIFICADO", "Alumno regular", "MEDIA"));
        assertEquals(0, servicio.listarPorEstado(Estado.ABIERTA).size());
    }

    @Test
    void rechazaCorreoInvalido() {
        SolicitudService servicio = new SolicitudService();
        Estudiante invalido = new Estudiante("12345", "Ana Perez", "correo-sin-arroba");
        assertThrows(IllegalArgumentException.class, () -> servicio.registrar(invalido, "CERTIFICADO", "Alumno regular", "MEDIA"));
    }

    @Test
    void rechazaPrioridadInvalida() {
        SolicitudService servicio = new SolicitudService();
        assertThrows(IllegalArgumentException.class, () -> servicio.registrar(estudiante, "CERTIFICADO", "Alumno regular", "URGENTE"));
    }

}

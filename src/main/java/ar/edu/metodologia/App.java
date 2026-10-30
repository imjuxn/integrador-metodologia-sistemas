package ar.edu.metodologia;

public class App {
    public static void main(String[] args) {
        SolicitudService servicio = new SolicitudService();
        Estudiante estudiante = new Estudiante("12345", "Ana Perez", "ana@example.com");
        Solicitud solicitud = servicio.registrar(estudiante, "CERTIFICADO", "Necesito certificado de alumno regular", "MEDIA");
        servicio.asignarResponsable(solicitud.getNumero(), new Responsable("Luis Gomez", "Alumnado"));
        servicio.cambiarEstado(solicitud.getNumero(), Estado.EN_PROCESO);

        System.out.println("Solicitudes en proceso: " + servicio.listarPorEstado(Estado.EN_PROCESO).size());
        System.out.print(servicio.generarReporte());
    }
}

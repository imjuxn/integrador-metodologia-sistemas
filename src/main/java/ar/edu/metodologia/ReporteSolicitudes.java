package ar.edu.metodologia;

import java.util.Collection;

public class ReporteSolicitudes {

    public String generar(Collection<Solicitud> solicitudes) {
        StringBuilder reporte = new StringBuilder();
        for (Solicitud solicitud : solicitudes) {
            reporte.append(solicitud.getNumero()).append(" | ")
                   .append(solicitud.getTipo()).append(" | ")
                   .append(solicitud.getEstado()).append("\n");
        }
        return reporte.toString();
    }
}
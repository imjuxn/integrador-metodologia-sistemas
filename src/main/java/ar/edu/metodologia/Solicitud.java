package ar.edu.metodologia;

public class Solicitud {
    private final int numero;
    private final Estudiante estudiante;
    private final String tipo;
    private final String detalle;
    private final String prioridad;
    private Estado estado = Estado.ABIERTA;
    private Responsable responsable;

    public Solicitud(int numero, Estudiante estudiante, String tipo, String detalle, String prioridad) {
        this.numero = numero;
        this.estudiante = estudiante;
        this.tipo = tipo;
        this.detalle = detalle;
        this.prioridad = prioridad;
    }

    public int getNumero() { return numero; }
    public Estudiante getEstudiante() { return estudiante; }
    public String getTipo() { return tipo; }
    public String getDetalle() { return detalle; }
    public String getPrioridad() { return prioridad; }
    public Estado getEstado() { return estado; }
    public Responsable getResponsable() { return responsable; }

    public void setEstado(Estado estado) { this.estado = estado; }
    public void setResponsable(Responsable responsable) { this.responsable = responsable; }
}

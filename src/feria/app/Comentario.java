package feria.app;

import java.time.LocalDateTime;

public class Comentario {

	private final int id;
    private final int visitanteId;
    private final int standNumero;
    private final LocalDateTime fecha;
    private final int calificacion; // 1..5
    private final String texto;

    public Comentario(int id, int visitanteId, int standNumero, LocalDateTime fecha, int calificacion, String texto) {
        this.id = id;
        this.visitanteId = visitanteId;
        this.standNumero = standNumero;
        this.fecha = fecha;
        this.calificacion = calificacion;
        this.texto = texto;
    }

    public int getId() { return id; }
    public int getVisitanteId() { return visitanteId; }
    public int getStandNumero() { return standNumero; }
    public LocalDateTime getFecha() { return fecha; }
    public int getCalificacion() { return calificacion; }
    public String getTexto() { return texto; }

    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", visitanteId=" + visitanteId +
                ", standNumero=" + standNumero +
                ", fecha=" + fecha +
                ", calificacion=" + calificacion +
                ", texto='" + texto + '\'' +
                '}';
    }
}

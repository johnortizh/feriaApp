
package com.mycompany.feria;


import java.time.LocalDate;

public class Comentario {
    private final String nombreVisitante;
    private final LocalDate fecha;
    private final int calificacion; // 1..5
    private final String texto;

    public Comentario(String nombreVisitante, LocalDate fecha, int calificacion, String texto) {
        if (calificacion < 1 || calificacion > 5)
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5.");
        this.nombreVisitante = nombreVisitante;
        this.fecha = fecha;
        this.calificacion = calificacion;
        this.texto = texto;
    }

    public String getNombreVisitante() { return nombreVisitante; }
    public LocalDate getFecha() { return fecha; }
    public int getCalificacion() { return calificacion; }
    public String getTexto() { return texto; }

    @Override public String toString() {
        return "[%s | %s | %d★] %s".formatted(nombreVisitante, fecha, calificacion, texto);
    }
}

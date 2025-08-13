
package com.mycompany.feria;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stand {
    private final int numero;              // único
    private String ubicacion;              // ej: "Pabellón A, Stand 10"
    private TamañoStand tamaño;
    private Empresa empresaAsignada;       // null = disponible
    private final List<Comentario> comentarios = new ArrayList<>();

    public Stand(int numero, String ubicacion, TamañoStand tamaño) {
        this.numero = numero;
        this.ubicacion = ubicacion;
        this.tamaño = tamaño;
    }

    // Gestión de asignación
    public void asignarEmpresa(Empresa e) { this.empresaAsignada = e; }
    public void desasignarEmpresa() { this.empresaAsignada = null; }
    public boolean estaOcupado() { return empresaAsignada != null; }

    // Edición básica del stand
    public void actualizar(String nuevaUbicacion, TamañoStand nuevoTamaño) {
        this.ubicacion = nuevaUbicacion;
        this.tamaño = nuevoTamaño;
    }

    // Comentarios
    public void agregarComentario(Comentario c) { comentarios.add(c); }
    public List<Comentario> getComentarios() { return Collections.unmodifiableList(comentarios); }

    // Getters
    public int getNumero() { return numero; }
    public String getUbicacion() { return ubicacion; }
    public TamañoStand getTamaño() { return tamaño; }
    public Empresa getEmpresaAsignada() { return empresaAsignada; }

    @Override public String toString() {
        String dueño = (empresaAsignada == null) ? "DISPONIBLE" : "Ocupado por: " + empresaAsignada.getNombre();
        return "#%d | %s | %s | %s".formatted(numero, ubicacion, tamaño, dueño);
    }
}

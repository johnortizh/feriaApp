
package com.mycompany.feria;


public class Visitante {
    private String nombre;
    private String identificacion;
    private String email;

    public Visitante(String nombre, String identificacion, String email) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.email = email;
    }

    public void actualizar(String nuevoNombre, String nuevaId, String nuevoEmail) {
        this.nombre = nuevoNombre;
        this.identificacion = nuevaId;
        this.email = nuevoEmail;
    }

    public String getNombre() { return nombre; }
    public String getIdentificacion() { return identificacion; }
    public String getEmail() { return email; }

    @Override public String toString() {
        return "%s [%s] - %s".formatted(nombre, identificacion, email);
    }
}


package com.mycompany.feria;


public class Empresa {
    private String nombre;
    private String sector;
    private String email;

    public Empresa(String nombre, String sector, String email) {
        this.nombre = nombre;
        this.sector = sector;
        this.email = email;
    }

    // Editar
    public void actualizar(String nuevoNombre, String nuevoSector, String nuevoEmail) {
        this.nombre = nuevoNombre;
        this.sector = nuevoSector;
        this.email = nuevoEmail;
    }

    public String getNombre() { return nombre; }
    public String getSector() { return sector; }
    public String getEmail() { return email; }

    @Override public String toString() {
        return "%s (%s) - %s".formatted(nombre, sector, email);
    }
}

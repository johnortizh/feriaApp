
package com.mycompany.feria;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class FeriaEmpresarial {
    private final Map<String, Empresa> empresas = new LinkedHashMap<>();        
    private final Map<Integer, Stand> stands = new LinkedHashMap<>();         
    private final Map<String, Visitante> visitantes = new LinkedHashMap<>();     

    /* EMPRESAS  */
    public boolean registrarEmpresa(Empresa e) {
        return empresas.putIfAbsent(e.getNombre().toLowerCase(), e) == null;
    }
    public Empresa buscarEmpresa(String nombre) {
        return empresas.get(nombre.toLowerCase());
    }
    public boolean editarEmpresa(String nombre, String nuevoNom, String nuevoSector, String nuevoEmail) {
        Empresa e = buscarEmpresa(nombre);
        if (e == null) return false;
        empresas.remove(nombre.toLowerCase());
        e.actualizar(nuevoNom, nuevoSector, nuevoEmail);
        empresas.put(nuevoNom.toLowerCase(), e);
        return true;
    }
    public boolean eliminarEmpresa(String nombre) {
        Empresa e = empresas.remove(nombre.toLowerCase());
        if (e == null) return false;
        // quitar stands ocupados por esa empresa
        for (Stand s : stands.values()) {
            if (s.getEmpresaAsignada() != null && s.getEmpresaAsignada().getNombre().equalsIgnoreCase(nombre)) {
                s.desasignarEmpresa();
            }
        }
        return true;
    }
    public List<Empresa> listarEmpresas() { return new ArrayList<>(empresas.values()); }

    /*  STANDS */
    public boolean registrarStand(Stand s) {
        return stands.putIfAbsent(s.getNumero(), s) == null;
    }
    public Stand buscarStand(int numero) { return stands.get(numero); }
    public boolean editarStand(int numero, String ubicacion, TamañoStand tamaño) {
        Stand s = buscarStand(numero);
        if (s == null) return false;
        s.actualizar(ubicacion, tamaño);
        return true;
    }
    public boolean eliminarStand(int numero) { return stands.remove(numero) != null; }

    public boolean asignarStandAEmpresa(int numeroStand, String nombreEmpresa) {
        Stand s = buscarStand(numeroStand);
        Empresa e = buscarEmpresa(nombreEmpresa);
        if (s == null || e == null || s.estaOcupado()) return false;
        s.asignarEmpresa(e);
        return true;
    }
    public boolean desasignarStand(int numeroStand) {
        Stand s = buscarStand(numeroStand);
        if (s == null || !s.estaOcupado()) return false;
        s.desasignarEmpresa();
        return true;
    }

    public List<Stand> listarStandsDisponibles() {
        return stands.values().stream().filter(s -> !s.estaOcupado()).collect(Collectors.toList());
    }
    public List<Stand> listarStandsOcupados() {
        return stands.values().stream().filter(Stand::estaOcupado).collect(Collectors.toList());
    }
    public List<Stand> listarStands() { return new ArrayList<>(stands.values()); }

    /* VISITANTES */
    public boolean registrarVisitante(Visitante v) {
        return visitantes.putIfAbsent(v.getIdentificacion(), v) == null;
    }
    public Visitante buscarVisitante(String id) { return visitantes.get(id); }
    public boolean editarVisitante(String id, String nuevoNombre, String nuevaId, String nuevoEmail) {
        Visitante v = buscarVisitante(id);
        if (v == null) return false;
        visitantes.remove(id);
        v.actualizar(nuevoNombre, nuevaId, nuevoEmail);
        visitantes.put(nuevaId, v);
        return true;
    }
    public boolean eliminarVisitante(String id) { return visitantes.remove(id) != null; }
    public List<Visitante> listarVisitantes() { return new ArrayList<>(visitantes.values()); }

    /* INTERACCIÓN (visitar + comentar) */
    public boolean dejarComentario(String idVisitante, int numeroStand, int calificacion, String texto) {
        Visitante v = buscarVisitante(idVisitante);
        Stand s = buscarStand(numeroStand);
        if (v == null || s == null) return false;
        Comentario c = new Comentario(v.getNombre(), LocalDate.now(), calificacion, texto);
        s.agregarComentario(c);
        return true;
    }

    /* REPORTES  */
    public String reporteEmpresasYStands() {
        StringBuilder sb = new StringBuilder("=== Empresas y sus Stands ===\n");
        for (Empresa e : empresas.values()) {
            List<Stand> misStands = stands.values().stream()
                    .filter(s -> s.getEmpresaAsignada() != null &&
                                 s.getEmpresaAsignada().getNombre().equalsIgnoreCase(e.getNombre()))
                    .toList();
            sb.append("- ").append(e.getNombre()).append(": ");
            if (misStands.isEmpty()) sb.append("(sin stands)\n");
            else sb.append(misStands.stream().map(st -> "#" + st.getNumero()).collect(Collectors.joining(", "))).append("\n");
        }
        return sb.toString();
    }

    public String reporteVisitantesYVisitas() {
        StringBuilder sb = new StringBuilder("=== Visitantes y Stands visitados (comentados) ===\n");
        for (Visitante v : visitantes.values()) {
            List<Integer> visitados = stands.values().stream()
                    .filter(s -> s.getComentarios().stream().anyMatch(c -> c.getNombreVisitante().equalsIgnoreCase(v.getNombre())))
                    .map(Stand::getNumero).toList();
            sb.append("- ").append(v.getNombre()).append(": ");
            sb.append(visitados.isEmpty() ? "(sin visitas registradas)\n"
                    : visitados.stream().map(n -> "#" + n).collect(Collectors.joining(", ")) + "\n");
        }
        return sb.toString();
    }
}

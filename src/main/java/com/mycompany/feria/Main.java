
package com.mycompany.feria;


import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final FeriaEmpresarial feria = new FeriaEmpresarial();

    public static void main(String[] args) {
        int op;
        do {
            menu();
            op = leerInt("Opción: ");
            switch (op) {
                case 1 -> registrarEmpresa();
                case 2 -> editarEmpresa();
                case 3 -> eliminarEmpresa();
                case 4 -> registrarStand();
                case 5 -> editarStand();
                case 6 -> eliminarStand();
                case 7 -> asignarStand();
                case 8 -> listarStands();
                case 9 -> registrarVisitante();
                case 10 -> editarVisitante();
                case 11 -> eliminarVisitante();
                case 12 -> visitarYComentar();
                case 13 -> verComentariosDeStand();
                case 14 -> System.out.println(feria.reporteEmpresasYStands());
                case 15 -> System.out.println(feria.reporteVisitantesYVisitas());
                case 0 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("Opción inválida.");
            }
            System.out.println();
        } while (op != 0);
    }

    /* MENÚ */
    private static void menu() {
        System.out.println("""
        ================= FERIA EMPRESARIAL =================
        1. Registrar empresa        2. Editar empresa         3. Eliminar empresa
        4. Registrar stand          5. Editar stand           6. Eliminar stand
        7. Asignar stand a empresa  8. Listar stands (disp/ocupados)
        9. Registrar visitante     10. Editar visitante      11. Eliminar visitante
        12. Visitar stand y comentar
        13. Ver comentarios de un stand
        14. Reporte: Empresas y sus stands
        15. Reporte: Visitantes y stands visitados
        0. Salir
        =====================================================
        """);
    }

    /* EMPRESAS */
    private static void registrarEmpresa() {
        System.out.println("\n-- Registrar empresa --");
        String nombre = leer("Nombre: ");
        String sector = leer("Sector: ");
        String email  = leer("Email: ");
        System.out.println(feria.registrarEmpresa(new Empresa(nombre, sector, email))
                ? "✔ Empresa registrada." : "✖ Ya existe una empresa con ese nombre.");
    }
    private static void editarEmpresa() {
        System.out.println("\n-- Editar empresa --");
        String actual = leer("Nombre actual: ");
        String nombre = leer("Nuevo nombre: ");
        String sector = leer("Nuevo sector: ");
        String email  = leer("Nuevo email: ");
        System.out.println(feria.editarEmpresa(actual, nombre, sector, email)
                ? "✔ Empresa actualizada." : "✖ No encontrada.");
    }
    private static void eliminarEmpresa() {
        System.out.println("\n-- Eliminar empresa --");
        String nombre = leer("Nombre: ");
        System.out.println(feria.eliminarEmpresa(nombre)
                ? "✔ Empresa eliminada (y stands desasignados)." : "✖ No encontrada.");
    }

    /* STANDS */
    private static void registrarStand() {
        System.out.println("\n-- Registrar stand --");
        int numero = leerInt("Número (único): ");
        String ubic = leer("Ubicación (ej. Pabellón A, Stand 10): ");
        TamañoStand tam = leerTamaño();
        System.out.println(feria.registrarStand(new Stand(numero, ubic, tam))
                ? "✔ Stand registrado." : "✖ Ya existe un stand con ese número.");
    }
    private static TamañoStand leerTamaño() {
        System.out.println("Tamaño: 1) PEQUEÑO  2) MEDIANO  3) GRANDE");
        int t = leerInt("Selecciona: ");
        return switch (t) { case 1 -> TamañoStand.PEQUEÑO; case 2 -> TamañoStand.MEDIANO; default -> TamañoStand.GRANDE; };
    }
    private static void editarStand() {
        System.out.println("\n-- Editar stand --");
        int numero = leerInt("Número: ");
        String ubic = leer("Nueva ubicación: ");
        TamañoStand tam = leerTamaño();
        System.out.println(feria.editarStand(numero, ubic, tam)
                ? "✔ Stand actualizado." : "✖ Stand no encontrado.");
    }
    private static void eliminarStand() {
        System.out.println("\n-- Eliminar stand --");
        int numero = leerInt("Número: ");
        System.out.println(feria.eliminarStand(numero)
                ? "✔ Stand eliminado." : "✖ Stand no encontrado.");
    }
    private static void asignarStand() {
        System.out.println("\n-- Asignar stand a empresa --");
        int numero = leerInt("Número de stand: ");
        String empresa = leer("Nombre de empresa: ");
        System.out.println(feria.asignarStandAEmpresa(numero, empresa)
                ? "✔ Stand asignado." : "✖ Stand/empresa no existe o el stand ya está ocupado.");
    }
    private static void listarStands() {
        System.out.println("\n-- Stands disponibles --");
        imprimirLista(feria.listarStandsDisponibles());
        System.out.println("\n-- Stands ocupados --");
        imprimirLista(feria.listarStandsOcupados());
    }

    /* VISITANTES */
    private static void registrarVisitante() {
        System.out.println("\n-- Registrar visitante --");
        String nombre = leer("Nombre: ");
        String id     = leer("Identificación: ");
        String email  = leer("Email: ");
        System.out.println(feria.registrarVisitante(new Visitante(nombre, id, email))
                ? "✔ Visitante registrado." : "✖ Ya existe un visitante con esa identificación.");
    }
    private static void editarVisitante() {
        System.out.println("\n-- Editar visitante --");
        String id = leer("Identificación actual: ");
        String nombre = leer("Nuevo nombre: ");
        String nuevaId = leer("Nueva identificación: ");
        String email = leer("Nuevo email: ");
        System.out.println(feria.editarVisitante(id, nombre, nuevaId, email)
                ? "✔ Visitante actualizado." : "✖ Visitante no encontrado.");
    }
    private static void eliminarVisitante() {
        System.out.println("\n-- Eliminar visitante --");
        String id = leer("Identificación: ");
        System.out.println(feria.eliminarVisitante(id)
                ? "✔ Visitante eliminado." : "✖ Visitante no encontrado.");
    }

    /* INTERACCIÓN */
    private static void visitarYComentar() {
        System.out.println("\n-- Visitar stand y dejar comentario --");
        String id = leer("ID del visitante: ");
        int numero = leerInt("Número de stand: ");
        int cal = Math.max(1, Math.min(5, leerInt("Calificación (1-5): ")));
        String texto = leer("Comentario: ");
        System.out.println(feria.dejarComentario(id, numero, cal, texto)
                ? "✔ Comentario registrado." : "✖ Visitante o stand no existe.");
    }
    private static void verComentariosDeStand() {
        System.out.println("\n-- Comentarios de un stand --");
        int numero = leerInt("Número de stand: ");
        Stand s = feria.buscarStand(numero);
        if (s == null) { System.out.println("✖ Stand no encontrado."); return; }
        List<Comentario> cs = s.getComentarios();
        if (cs.isEmpty()) System.out.println("(Sin comentarios)");
        else cs.forEach(c -> System.out.println(" - " + c));
    }

    /* ===== Utilidades ===== */
    private static void imprimirLista(List<?> l) {
        if (l.isEmpty()) System.out.println("(Vacío)"); else l.forEach(e -> System.out.println(" - " + e));
    }
    private static String leer(String msg) { System.out.print(msg); return sc.nextLine().trim(); }
    private static int leerInt(String msg) {
        while (true) {
            try { return Integer.parseInt(leer(msg)); }
            catch (NumberFormatException e) { System.out.println("Ingresa un número válido."); }
        }
    }
}


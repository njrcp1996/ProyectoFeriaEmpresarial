package feria.app;

import java.util.List;
import java.util.Scanner;

public class FeriaEmpresarialMain {

	private static final Scanner sc = new Scanner(System.in);
    private static final Feria service = new Feria();

    public static void main(String[] args) {
        precargarDatosDemo(); // opcional, para probar
        boolean salir = false;
        while (!salir) {
            System.out.println("\n===== FERIA EMPRESARIAL =====");
            System.out.println("1. Empresas");
            System.out.println("2. Stands");
            System.out.println("3. Visitantes");
            System.out.println("4. Interacciones (visitas/comentarios)");
            System.out.println("5. Reportes");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            int op = leerEntero();
            switch (op) {
                case 1 -> menuEmpresas();
                case 2 -> menuStands();
                case 3 -> menuVisitantes();
                case 4 -> menuInteracciones();
                case 5 -> menuReportes();
                case 0 -> salir = true;
                default -> System.out.println("Opción inválida");
            }
        }
        System.out.println("¡Hasta luego!");
    }

    // ================= MENÚ EMPRESAS =================
    private static void menuEmpresas() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n-- EMPRESAS --");
            System.out.println("1. Registrar empresa");
            System.out.println("2. Editar empresa");
            System.out.println("3. Eliminar empresa");
            System.out.println("4. Listar empresas");
            System.out.println("5. Asignar stand a empresa");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            int op = leerEntero();
            switch (op) {
                case 1 -> registrarEmpresa();
                case 2 -> editarEmpresa();
                case 3 -> eliminarEmpresa();
                case 4 -> listarEmpresas();
                case 5 -> asignarStandAEmpresa();
                case 0 -> volver = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void registrarEmpresa() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Sector: ");
        String sector = sc.nextLine();
        System.out.print("Email contacto: ");
        String email = sc.nextLine();
        Empresa e = service.crearEmpresa(nombre, sector, email);
        System.out.println("Creada: " + e);
    }

    private static void editarEmpresa() {
        listarEmpresas();
        System.out.print("ID de empresa a editar: ");
        int id = leerEntero();
        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Nuevo sector: ");
        String sector = sc.nextLine();
        System.out.print("Nuevo email: ");
        String email = sc.nextLine();
        boolean ok = service.editarEmpresa(id, nombre, sector, email);
        System.out.println(ok ? "Actualizada" : "No encontrada");
    }

    private static void eliminarEmpresa() {
        listarEmpresas();
        System.out.print("ID de empresa a eliminar: ");
        int id = leerEntero();
        boolean ok = service.eliminarEmpresa(id);
        System.out.println(ok ? "Eliminada" : "No encontrada");
    }

    private static void listarEmpresas() {
        List<Empresa> lista = service.listarEmpresas();
        if (lista.isEmpty()) { System.out.println("No hay empresas"); return; }
        lista.forEach(System.out::println);
    }

    private static void asignarStandAEmpresa() {
        listarEmpresas();
        System.out.print("ID de empresa: ");
        int empId = leerEntero();
        listarStands();
        System.out.print("Número de stand a asignar: ");
        int standNum = leerEntero();
        String res = service.asignarStandAEmpresa(empId, standNum);
        System.out.println(res.equals("OK") ? "Asignación realizada" : res);
    }

    // ================= MENÚ STANDS =================
    private static void menuStands() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n-- STANDS --");
            System.out.println("1. Crear stand");
            System.out.println("2. Editar stand");
            System.out.println("3. Eliminar stand");
            System.out.println("4. Listar todos");
            System.out.println("5. Listar libres");
            System.out.println("6. Listar ocupados");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            int op = leerEntero();
            switch (op) {
                case 1 -> crearStand();
                case 2 -> editarStand();
                case 3 -> eliminarStand();
                case 4 -> listarStands();
                case 5 -> listarStandsLibres();
                case 6 -> listarStandsOcupados();
                case 0 -> volver = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void crearStand() {
        System.out.print("Número único: ");
        int numero = leerEntero();
        System.out.print("Ubicación (ej: Pabellón A, Stand 10): ");
        String ubic = sc.nextLine();
        System.out.print("Tamaño (pequeño/mediano/grande): ");
        String tam = sc.nextLine();
        boolean ok = service.crearStand(numero, ubic, tam);
        System.out.println(ok ? "Stand creado" : "El número ya existe");
    }

    private static void editarStand() {
        listarStands();
        System.out.print("Número de stand a editar: ");
        int numero = leerEntero();
        System.out.print("Nueva ubicación: ");
        String ubic = sc.nextLine();
        System.out.print("Nuevo tamaño: ");
        String tam = sc.nextLine();
        boolean ok = service.editarStand(numero, ubic, tam);
        System.out.println(ok ? "Actualizado" : "No encontrado");
    }

    private static void eliminarStand() {
        listarStands();
        System.out.print("Número de stand a eliminar: ");
        int numero = leerEntero();
        boolean ok = service.eliminarStand(numero);
        System.out.println(ok ? "Eliminado" : "No encontrado");
    }

    private static void listarStands() {
        List<Stand> lista = service.listarStands();
        if (lista.isEmpty()) { System.out.println("No hay stands"); return; }
        lista.forEach(System.out::println);
    }

    private static void listarStandsLibres() {
        List<Stand> lista = service.standsLibres();
        if (lista.isEmpty()) { System.out.println("No hay stands libres"); return; }
        lista.forEach(System.out::println);
    }

    private static void listarStandsOcupados() {
        List<Stand> lista = service.standsOcupados();
        if (lista.isEmpty()) { System.out.println("No hay stands ocupados"); return; }
        lista.forEach(System.out::println);
    }

    // ================= MENÚ VISITANTES =================
    private static void menuVisitantes() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n-- VISITANTES --");
            System.out.println("1. Registrar visitante");
            System.out.println("2. Editar visitante");
            System.out.println("3. Eliminar visitante");
            System.out.println("4. Listar visitantes");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            int op = leerEntero();
            switch (op) {
                case 1 -> registrarVisitante();
                case 2 -> editarVisitante();
                case 3 -> eliminarVisitante();
                case 4 -> listarVisitantes();
                case 0 -> volver = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void registrarVisitante() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Identificación: ");
        String ident = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        Visitante v = service.crearVisitante(nombre, ident, email);
        System.out.println("Creado: " + v);
    }

    private static void editarVisitante() {
        listarVisitantes();
        System.out.print("ID de visitante a editar: ");
        int id = leerEntero();
        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Nueva identificación: ");
        String ident = sc.nextLine();
        System.out.print("Nuevo email: ");
        String email = sc.nextLine();
        boolean ok = service.editarVisitante(id, nombre, ident, email);
        System.out.println(ok ? "Actualizado" : "No encontrado");
    }

    private static void eliminarVisitante() {
        listarVisitantes();
        System.out.print("ID de visitante a eliminar: ");
        int id = leerEntero();
        boolean ok = service.eliminarVisitante(id);
        System.out.println(ok ? "Eliminado" : "No encontrado");
    }

    private static void listarVisitantes() {
        List<Visitante> lista = service.listarVisitantes();
        if (lista.isEmpty()) { System.out.println("No hay visitantes"); return; }
        lista.forEach(System.out::println);
    }

    // ================= MENÚ INTERACCIONES =================
    private static void menuInteracciones() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n-- INTERACCIONES --");
            System.out.println("1. Registrar visita/comentario a un stand");
            System.out.println("2. Ver comentarios por empresa");
            System.out.println("3. Ver comentarios por stand");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            int op = leerEntero();
            switch (op) {
                case 1 -> registrarComentario();
                case 2 -> verComentariosPorEmpresa();
                case 3 -> verComentariosPorStand();
                case 0 -> volver = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void registrarComentario() {
        listarVisitantes();
        System.out.print("ID de visitante: ");
        int visitanteId = leerEntero();
        listarStands();
        System.out.print("Número de stand visitado: ");
        int standNumero = leerEntero();
        System.out.print("Calificación (1 a 5): ");
        int cal = leerEntero();
        System.out.print("Comentario: ");
        String texto = sc.nextLine();
        String res = service.registrarComentario(visitanteId, standNumero, cal, texto);
        System.out.println(res.equals("OK") ? "Comentario registrado" : res);
    }

    private static void verComentariosPorEmpresa() {
        listarEmpresas();
        System.out.print("ID de empresa: ");
        int empId = leerEntero();
        List<Comentario> lista = service.comentariosPorEmpresa(empId);
        if (lista.isEmpty()) { System.out.println("Sin comentarios"); return; }
        lista.forEach(System.out::println); // Imprimir linea a linea los comentarios
    }

    private static void verComentariosPorStand() {
        listarStands();
        System.out.print("Número de stand: ");
        int standNum = leerEntero();
        List<Comentario> lista = service.comentariosPorStand(standNum);
        if (lista.isEmpty()) { System.out.println("Sin comentarios"); return; }
        lista.forEach(System.out::println);
    }

    // ================= MENÚ REPORTES =================
    private static void menuReportes() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n-- REPORTES --");
            System.out.println("1. Empresas y stands asignados");
            System.out.println("2. Visitantes y stands visitados");
            System.out.println("3. Promedio de calificaciones por stand");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            int op = leerEntero();
            switch (op) {
                case 1 -> service.reporteEmpresasYStands().forEach(System.out::println);
                case 2 -> service.reporteVisitantesYVisitas().forEach(System.out::println);
                case 3 -> service.reportePromedioPorStand().forEach(System.out::println);
                case 0 -> volver = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    // ================= UTILIDADES =================
    private static int leerEntero() {
        while (true) {
            String line = sc.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }

    private static void precargarDatosDemo() {
        // Stands
        service.crearStand(10, "Pabellón A, Stand 10", "mediano");
        service.crearStand(11, "Pabellón A, Stand 11", "grande");
        service.crearStand(20, "Pabellón B, Stand 20", "pequeño");
        // Empresas
        var e1 = service.crearEmpresa("TechNova", "tecnología", "contacto@technova.com");
        var e2 = service.crearEmpresa("SaludPlus", "salud", "info@saludplus.com");
        // Asignación
        service.asignarStandAEmpresa(e1.getId(), 10);
        service.asignarStandAEmpresa(e2.getId(), 11);
        // Visitantes
        var v1 = service.crearVisitante("Ana Pérez", "12345678", "ana@mail.com");
        var v2 = service.crearVisitante("Carlos Ruiz", "87654321", "carlos@mail.com");
        // Comentarios demo
        service.registrarComentario(v1.getId(), 10, 5, "Muy buen stand, personal amable.");
        service.registrarComentario(v2.getId(), 10, 4, "Interesante tecnología.");
        service.registrarComentario(v1.getId(), 11, 3, "Faltó demostración.");
    }

}

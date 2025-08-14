package feria.app;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Feria {

	// "Persistencia" en memoria
    private final List<Empresa> empresas = new ArrayList<>();
    private final List<Stand> stands = new ArrayList<>();
    private final List<Visitante> visitantes = new ArrayList<>();
    private final List<Comentario> comentarios = new ArrayList<>();

    // generadores de id simples
    private int seqEmpresa = 1;
    private int seqVisitante = 1;
    private int seqComentario = 1;

    // ================= EMPRESAS =================
    public Empresa crearEmpresa(String nombre, String sector, String email) {
        Empresa e = new Empresa(seqEmpresa++, nombre, sector, email);
        empresas.add(e);
        return e;
    }

    public boolean editarEmpresa(int id, String nombre, String sector, String email) {
        Empresa e = buscarEmpresa(id);
        if (e == null) return false;
        e.setNombre(nombre);
        e.setSector(sector);
        e.setEmailContacto(email);
        return true;
    }

    public boolean eliminarEmpresa(int id) {
        Empresa e = buscarEmpresa(id);
        if (e == null) return false;
        // liberar stand si tenía
        if (e.getStandNumero() != null) {
            Stand s = buscarStand(e.getStandNumero());
            if (s != null) s.setEmpresaId(null);
        }
        return empresas.remove(e);
    }

    public Empresa buscarEmpresa(int id) {
        return empresas.stream().filter(x -> x.getId() == id).findFirst().orElse(null); // Se recorre la lista de empresas y se va a evaluar cada id guardado con el de la entrada y con findFist tomo el elemento de la lista encontrado
    }

    public List<Empresa> listarEmpresas() { return new ArrayList<>(empresas); } // Retorna la informacion de lo que exista actualmente

    // ================= STANDS =================
    public boolean crearStand(int numero, String ubicacion, String tamano) {
        if (buscarStand(numero) != null) return false; // número único
        Stand s = new Stand(numero, ubicacion, tamano);
        stands.add(s);
        return true;
    }

    public boolean editarStand(int numero, String ubicacion, String tamano) {
        Stand s = buscarStand(numero);
        if (s == null) return false;
        s.setUbicacion(ubicacion);
        s.setTamano(tamano);
        return true;
    }

    public boolean eliminarStand(int numero) {
        Stand s = buscarStand(numero);
        if (s == null) return false;
        // si estaba asignado, desasignar también en la empresa
        if (s.getEmpresaId() != null) {
            Empresa e = buscarEmpresa(s.getEmpresaId());
            if (e != null) e.setStandNumero(null);
        }
        return stands.remove(s);
    }

    public Stand buscarStand(int numero) {
        return stands.stream().filter(x -> x.getNumero() == numero).findFirst().orElse(null);
    }

    public List<Stand> listarStands() { return new ArrayList<>(stands); }

    public List<Stand> standsLibres() {
        return stands.stream().filter(Stand::estaLibre).collect(Collectors.toList());
    }

    public List<Stand> standsOcupados() {
        return stands.stream().filter(s -> !s.estaLibre()).collect(Collectors.toList());
    }

    // Asignar empresa a stand (único):
    public String asignarStandAEmpresa(int empresaId, int standNumero) {
        Empresa e = buscarEmpresa(empresaId);
        if (e == null) return "Empresa no encontrada";
        Stand s = buscarStand(standNumero);
        if (s == null) return "Stand no encontrado";
        if (!s.estaLibre()) return "El stand ya está asignado a otra empresa";
        // si la empresa ya tenía uno, lo liberamos antes de reasignar
        if (e.getStandNumero() != null) {
            Stand anterior = buscarStand(e.getStandNumero());
            if (anterior != null) anterior.setEmpresaId(null);
        }
        s.setEmpresaId(e.getId());
        e.setStandNumero(s.getNumero());
        return "OK";
    }

    // ================= VISITANTES =================
    public Visitante crearVisitante(String nombre, String identificacion, String email) {
        Visitante v = new Visitante(seqVisitante++, nombre, identificacion, email);
        visitantes.add(v);
        return v;
    }

    public boolean editarVisitante(int id, String nombre, String identificacion, String email) {
        Visitante v = buscarVisitante(id);
        if (v == null) return false;
        v.setNombre(nombre);
        v.setIdentificacion(identificacion);
        v.setEmail(email);
        return true;
    }

    public boolean eliminarVisitante(int id) {
        Visitante v = buscarVisitante(id);
        if (v == null) return false;
        return visitantes.remove(v);
    }

    public Visitante buscarVisitante(int id) {
        return visitantes.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public List<Visitante> listarVisitantes() { return new ArrayList<>(visitantes); }

    // ================= INTERACCIONES =================
    public String registrarComentario(int visitanteId, int standNumero, int calificacion, String texto) {
        Visitante v = buscarVisitante(visitanteId);
        if (v == null) return "Visitante no encontrado";
        Stand s = buscarStand(standNumero);
        if (s == null) return "Stand no encontrado";
        if (calificacion < 1) calificacion = 1;
        if (calificacion > 5) calificacion = 5;
        Comentario c = new Comentario(seqComentario++, visitanteId, standNumero, LocalDateTime.now(), calificacion, texto);
        comentarios.add(c);
        return "OK";
    }

    public List<Comentario> comentariosPorStand(int standNumero) {
        return comentarios.stream().filter(c -> c.getStandNumero() == standNumero).collect(Collectors.toList()); // retorna el listado de comentarios
    }

    public List<Comentario> comentariosPorEmpresa(int empresaId) {
        Empresa e = buscarEmpresa(empresaId);
        if (e == null || e.getStandNumero() == null) return Collections.emptyList(); // Retorna una lista vacia 
        return comentariosPorStand(e.getStandNumero());
    }

    // ================= REPORTES =================
    public List<String> reporteEmpresasYStands() {
        return empresas.stream()
                .sorted(Comparator.comparing(Empresa::getId)) // organiza verticalmente la informacion
                .map(e -> { // Se utiliza map para manipular la informacion de la lista acorde al tipo de dato que requiero guardar
                    String standStr = (e.getStandNumero() == null) ? "(sin stand)" : "Stand " + e.getStandNumero();
                    return String.format("[%d] %s | Sector: %s | %s", e.getId(), e.getNombre(), e.getSector(), standStr);
                })
                .collect(Collectors.toList());
    }

    public List<String> reporteVisitantesYVisitas() {
        // para cada visitante, listar los stands que comentó/visitó
        Map<Integer, List<Comentario>> porVisitante = comentarios.stream().collect(Collectors.groupingBy(Comentario::getVisitanteId)); // A
        return visitantes.stream()
                .sorted(Comparator.comparing(Visitante::getId))
                .map(v -> {
                    List<Comentario> cs = porVisitante.getOrDefault(v.getId(), Collections.emptyList()); // getOrDefault me trae los comentarios o vacio
                    String visited = cs.stream().map(c -> String.valueOf(c.getStandNumero())).distinct().collect(Collectors.joining(", ")); // Separo por comas las visitas de los stans y guardo esa informacion en la variable visited
                    if (visited.isEmpty()) visited = "(sin visitas)";
                    return String.format("[%d] %s | Visitas a stands: %s", v.getId(), v.getNombre(), visited);
                })
                .collect(Collectors.toList());
    }

    public List<String> reportePromedioPorStand() {
        Map<Integer, Double> promedio = comentarios.stream()
                .collect(Collectors.groupingBy(Comentario::getStandNumero, Collectors.averagingInt(Comentario::getCalificacion)));
        // asegurar que también aparezcan stands sin comentarios
        return stands.stream()
                .sorted(Comparator.comparingInt(Stand::getNumero))
                .map(s -> {
                    Double p = promedio.get(s.getNumero());
                    String prom = (p == null) ? "(sin calificaciones)" : String.format(Locale.US, "%.2f", p);
                    return String.format("Stand %d | %s | Tamaño: %s | Promedio: %s", s.getNumero(), s.getUbicacion(), s.getTamano(), prom);
                })
                .collect(Collectors.toList());
    }
}



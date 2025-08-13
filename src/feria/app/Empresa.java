package feria.app;

import java.util.Objects;

public class Empresa {

	private final int id;
    private String nombre;
    private String sector; // tecnologia, salud, educación, etc.
    private String emailContacto;
    // relación: por numero
    private Integer standNumero; // null si no asignado

    public Empresa(int id, String nombre, String sector, String emailContacto) {
        this.id = id;
        this.nombre = nombre;
        this.sector = sector;
        this.emailContacto = emailContacto;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }
    public String getEmailContacto() { return emailContacto; }
    public void setEmailContacto(String emailContacto) { this.emailContacto = emailContacto; }
    public Integer getStandNumero() { return standNumero; }
    public void setStandNumero(Integer standNumero) { this.standNumero = standNumero; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empresa)) return false;
        Empresa empresa = (Empresa) o;
        return id == empresa.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", sector='" + sector + '\'' +
                ", email='" + emailContacto + '\'' +
                ", standNumero=" + (standNumero == null ? "(sin asignar)" : standNumero) +
                '}';
    }
}

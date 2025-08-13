package feria.app;

import java.util.Objects;

public class Visitante {

	 private final int id;
	    private String nombre;
	    private String identificacion; // cédula/pasaporte
	    private String email;

	    public Visitante(int id, String nombre, String identificacion, String email) {
	        this.id = id;
	        this.nombre = nombre;
	        this.identificacion = identificacion;
	        this.email = email;
	    }

	    public int getId() { return id; }
	    public String getNombre() { return nombre; }
	    public void setNombre(String nombre) { this.nombre = nombre; }
	    public String getIdentificacion() { return identificacion; }
	    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Visitante)) return false;
	        Visitante that = (Visitante) o;
	        return id == that.id;
	    }

	    @Override
	    public int hashCode() { return Objects.hash(id); }

	    @Override
	    public String toString() {
	        return "Visitante{" +
	                "id=" + id +
	                ", nombre='" + nombre + '\'' +
	                "', identificacion='" + identificacion + '\'' +
	                "', email='" + email + '\'' +
	                '}';
	    }
}

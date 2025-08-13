package feria.app;

import java.util.Objects;

public class Stand {

	 private final int numero; // único
	    private String ubicacion; // Ej: "Pabellón A, Stand 10"
	    private String tamano;    // pequeño, mediano, grande
	    private Integer empresaId; // null si libre

	    public Stand(int numero, String ubicacion, String tamano) {
	        this.numero = numero;
	        this.ubicacion = ubicacion;
	        this.tamano = tamano;
	    }

	    public int getNumero() { return numero; }
	    public String getUbicacion() { return ubicacion; }
	    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
	    public String getTamano() { return tamano; }
	    public void setTamano(String tamano) { this.tamano = tamano; }
	    public Integer getEmpresaId() { return empresaId; }
	    public void setEmpresaId(Integer empresaId) { this.empresaId = empresaId; }

	    public boolean estaLibre() { return empresaId == null; }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Stand)) return false;
	        Stand stand = (Stand) o;
	        return numero == stand.numero;
	    }

	    @Override
	    public int hashCode() { return Objects.hash(numero); }

	    @Override
	    public String toString() {
	        return "Stand{" +
	                "numero=" + numero +
	                ", ubicacion='" + ubicacion + '\'' +
	                ", tamano='" + tamano + '\'' +
	                ", estado=" + (estaLibre() ? "LIBRE" : ("OCUPADO por empresaId=" + empresaId)) +
	                '}';
	    }
}

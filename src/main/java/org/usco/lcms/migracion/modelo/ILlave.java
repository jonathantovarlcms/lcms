package org.usco.lcms.migracion.modelo;

import java.util.Map;

public interface ILlave {
	void setEsPrimaria(Boolean primaria);
	Boolean getEsPrimaria();
	void setNombre(String nombre);
	String getNombre();
	Map<String, Boolean> getColumnas();
}

package org.usco.lcms.Migracion.Modelo;

import java.util.Map;

public interface ITabla {
	void setNombre(String nombre);
	String getNombre();
	Map<String, IColumna> getColumnas();
}

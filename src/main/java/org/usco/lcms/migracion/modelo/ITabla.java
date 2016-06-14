package org.usco.lcms.migracion.modelo;

import java.util.Map;

public interface ITabla {
	void setComentarios(String comentarios);
	String getComentarios();
	void setEsquema(String esquema);
	String getEsquema();
	void setNombre(String nombre);
	String getNombre();
	Map<String, IColumna> getColumnas();
	Map<String, ILlave> getLlaves();
	void migrar();
	void migrarTabla();
	StringBuilder obtenerSql();
}

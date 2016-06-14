package org.usco.lcms.migracion.modelo;

import java.util.Map;

public interface IEsquema {
	void setNombre(String nombre);
	String getNombre();
	Map<String, ITabla> getTablas();
	void migrar();
	void migrarEsquema();
	StringBuilder obtenerSql();
}

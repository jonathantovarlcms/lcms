package org.usco.lcms.Migracion.Modelo;

import java.util.Map;

public interface IEsquema {
	void setNombre(String nombre);
	String getNombre();
	Map<String, ITabla> getTablas();
	void migrar();
	StringBuilder obtenerSql();
}

package org.usco.lcms.migracion.modelo;

import java.util.Map;

import org.usco.lcms.migracion.conectores.IConector;

public interface IModelo {
	IConector getConector();
	Map<String, IEsquema> getEsquemas();
	String getTipo();
	void migrarDesde(IModelo modelo);
	StringBuilder obtenerSql();
	void preparar();
}

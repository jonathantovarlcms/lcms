package org.usco.lcms.Migracion.Modelo;

import java.util.Map;

import org.usco.lcms.Migracion.Conectores.IConector;

public interface IModelo {
	IConector getConector();
	Map<String, IEsquema> getEsquemas();
	String getTipo();
	void migrarDesde(IModelo modelo);
	StringBuilder obtenerSql();
	void preparar();
}

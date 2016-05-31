package org.usco.lcms.Migracion.Modelo;

import java.util.HashMap;
import java.util.Map;

public class TablaSqlServer implements ITabla {
	private String nombre;
	private Map<String, IColumna> columnas;
	
	/**
	 * Constructor
	 * 
	 */
	public TablaSqlServer() {
		super();
		
		this.columnas = new HashMap<String, IColumna>();
	}

	/**
	 * @return Nombre de la tabla
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre Nombre de la tabla
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return Devuelve la lista de columnas de la tabla
	 */
	public Map<String, IColumna> getColumnas() {
		return this.columnas;
	}
}

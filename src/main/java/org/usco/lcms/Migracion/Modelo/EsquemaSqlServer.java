package org.usco.lcms.Migracion.Modelo;

import java.util.HashMap;
import java.util.Map;

public class EsquemaSqlServer implements IEsquema {
	private String nombre;
	private Map<String, ITabla> tablas;
	
	/**
	 * Constructor
	 * 
	 */
	public EsquemaSqlServer() {
		super();
		
		this.tablas = new HashMap<String, ITabla>();
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return Devuelve la lista de tablas que componen el esquema
	 */
	public Map<String, ITabla> getTablas() {
		return this.tablas;
	}
}

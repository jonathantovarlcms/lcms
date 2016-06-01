package org.usco.lcms.Migracion.Modelo;

import java.util.HashMap;
import java.util.Map;

public class EsquemaSqlServer implements IEsquema {
	private String nombre;
	private Map<String, ITabla> listaTablas;
	
	/**
	 * Constructor
	 * 
	 */
	public EsquemaSqlServer() {
		super();
		
		this.listaTablas = new HashMap<String, ITabla>();
	}

	/**
	 * @return Nombre del esquema
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre Nombre del esquema
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return Devuelve la lista de tablas que componen el esquema
	 */
	public Map<String, ITabla> getTablas() {
		return this.listaTablas;
	}
	
	/**
	 * Construye y devuelve la cadena SQL para construir el esquema
	 * 
	 * @return StringBuilder Cadena SQL
	 */
	public StringBuilder obtenerSql() {
		StringBuilder retorno = new StringBuilder();
		
		if (!this.nombre.equals("dbo")) {
			retorno.append("CREATE SCHEMA ["+ this.nombre +"] AUTHORIZATION [dbo];\n");
			retorno.append("GO\n\n");
		}
		
		for(ITabla tabla: this.listaTablas.values()) {
			retorno.append(tabla.obtenerSql());
			retorno.append("GO\n\n");
		}
		
		return retorno;
	}
}

package org.usco.lcms.Migracion.Modelo;

import java.util.HashMap;
import java.util.Map;

public class TablaSqlServer implements ITabla {
	private String comentarios;
	private String esquema;
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
	 * @return Devuelve los comentarios asociados a la tabla
	 */
	public String getComentarios() {
		return this.comentarios;
	}

	/**
	 * @param comentarios Comentarios a asociar con la tabla
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	
	/**
	 * @return Nombre del esquema
	 */
	public String getEsquema() {
		return this.esquema;
	}

	/**
	 * @param esquema Nombre del esquema a asignar
	 */
	public void setEsquema(String esquema) {
		this.esquema = esquema;
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
	
	/**
	 * Construye y devuelve la cadena SQL para construir la tabla
	 * 
	 * @return StringBuilder Cadena SQL
	 */
	public StringBuilder obtenerSql() {
		StringBuilder retorno = new StringBuilder();
		
		retorno.append("CREATE TABLE ["+this.esquema+"].["+this.nombre+"](\n");
		retorno.append(") ON [PRIMARY]\n");
		
		return retorno;
	}
}

package org.usco.lcms.Migracion.Modelo;

import java.util.HashMap;
import java.util.Map;

/**
 * Definicion de una llave para la base de dato SQL Server
 * 
 * @author Jonathan Tovar Sanmiguel
 */
public class LlaveSqlServer implements ILlave {
	private String nombre;
	private Boolean primaria;
	private Map<String, Boolean> listaColumnas;

	/**
	 * Constructor
	 */
	public LlaveSqlServer() {
		super();
		
		this.listaColumnas = new HashMap<String, Boolean>();
	}
	
	/**
	 * Constructor
	 * 
	 * @param nombre
	 * @param primaria
	 * @param descendente
	 */
	public LlaveSqlServer(String nombre, Boolean primaria) {
		super();
		this.nombre = nombre;
		this.primaria = primaria;
		
		this.listaColumnas = new HashMap<String, Boolean>();
	}
	
	/**
	 * @param primaria Indicador de llave primaria
	 */
	public void setEsPrimaria(Boolean primaria) {
		this.primaria = primaria;
	}
	
	/**
	 * @return Devuelve le indicador de llave primaria
	 */
	public Boolean getEsPrimaria() {
		return this.primaria;
	}
	
	/**
	 * @param nombre El nombre de la llave
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return Devuelve el nombre de la llave
	 */
	public String getNombre() {
		return this.nombre;
	}
	
	/**
	 * @return Lista de columnas que componen la llave
	 */
	public Map<String, Boolean> getColumnas() {
		return this.listaColumnas;
	}
}

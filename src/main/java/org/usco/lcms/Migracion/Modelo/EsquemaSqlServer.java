package org.usco.lcms.Migracion.Modelo;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.usco.lcms.Migracion.Conectores.IConector;

public class EsquemaSqlServer implements IEsquema {
	private IModelo modelo;
	private String nombre;
	private Map<String, ITabla> listaTablas;
	
	/**
	 * Constructor
	 * 
	 */
	public EsquemaSqlServer(IModelo modelo) {
		super();
		
		this.modelo = modelo;
		this.listaTablas = new HashMap<String, ITabla>();
	}
	
	/**
	 * Constructor basado en otro esquema
	 * 
	 */
	public EsquemaSqlServer(IModelo modelo, IEsquema esquema) {
		super();
		
		this.modelo = modelo;
		this.listaTablas = new HashMap<String, ITabla>();
		
		this.setNombre(esquema.getNombre());
		ITabla tabla, tablaReferencia;
		Map<String, ITabla> listaTablasReferencia = esquema.getTablas();
		for(String nombreTabla: listaTablasReferencia.keySet()) {
			tablaReferencia = listaTablasReferencia.get(nombreTabla);
			tabla = new TablaSqlServer(modelo, tablaReferencia);
			this.listaTablas.put(nombreTabla, tabla);
		}
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
	 * Realiza la migracion al modelo configurado
	 */
	public void migrar() {
		Statement sentencia = null;
		IConector conector = this.modelo.getConector();
		try {
			conector.conectar();
			sentencia = conector.getConexion().createStatement();
			
			if (!this.nombre.equals("dbo")) {
				sentencia.execute("CREATE SCHEMA ["+ this.nombre +"] AUTHORIZATION [dbo]");
			}
			for(ITabla tabla: this.listaTablas.values()) {
				tabla.migrar();
			}
        } catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
	         if (sentencia != null) try { sentencia.close(); } catch(Exception e) {}
        }
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
		}
		
		return retorno;
	}
}

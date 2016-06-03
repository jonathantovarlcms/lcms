package org.usco.lcms.Migracion.Modelo;

import java.sql.SQLException;
import java.util.Map;

import org.usco.lcms.Migracion.TiposModeloBaseDatos;
import org.usco.lcms.Migracion.Conectores.ConectorPostgres;
import org.usco.lcms.Migracion.Conectores.IConector;

/**
 * @author Jonathan Tovar Sanmiguel
 */
public class ModeloPostgres implements IModelo {
	private IConector conector;
	private Map<String, IEsquema> listaEsquemas; 
	
	/**
	 * Constructor
	 * 
	 * @param conector
	 */
	public ModeloPostgres(ConectorPostgres conector) {
		super();
		
		this.conector = conector;
		try {
			this.conector.conectar();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return Devuelve el conector a la base de datos
	 */
	public IConector getConector() {
		return this.conector;
	}
	
	/**
	 * @return Devuelve la lista de esquemas del modelo
	 */
	public Map<String, IEsquema> getEsquemas() {
		return this.listaEsquemas;
	}
	
	/**
	 * @return Devuelve el tipo de modelo de base de datos
	 */
	public String getTipo() {
		return TiposModeloBaseDatos.POSTGRES;
	}
	
	/**
	 * Migra el modelo actual de base de datos a uno de destino
	 * 
	 * @param modelo Modelo de la base de datos de destino
	 */
	public void migrarDesde(IModelo modelo) {
		
	}
	
	/**
	 * Construye y devuelve la cadena SQL para construir el modelo
	 */
	public StringBuilder obtenerSql() {
		StringBuilder retorno = new StringBuilder();
		
		return retorno;
	}

	/**
	 * Obtiene la información de la base de datos y construye e interpreta su estructura
	 */
	public void preparar() {
		throw new UnsupportedOperationException("Funcion aún no desarrollada");
	}
}

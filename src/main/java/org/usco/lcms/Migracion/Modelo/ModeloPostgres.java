package org.usco.lcms.Migracion.Modelo;

import java.sql.SQLException;

import org.usco.lcms.Migracion.Conectores.IConector;

/**
 * @author Jonathan Tovar Sanmiguel
 */
public class ModeloPostgres implements IModelo {
	private IConector conector;
	
	/**
	 * Constructor
	 * 
	 * @param servidor
	 * @param baseDatos
	 * @param usuario
	 * @param clave
	 */
	public ModeloPostgres(IConector conector) {
		super();
		
		this.conector = conector;
		try {
			this.conector.conectar();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene la información de la base de datos y construye e interpreta su estructura
	 */
	public void preparar() {
		throw new UnsupportedOperationException("Funcion aún no desarrollada");
	}
}

package org.usco.lcms.Migracion.Modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.usco.lcms.Migracion.Conectores.IConector;

/**
 * @author Jonathan Tovar Sanmiguel
 */
public class ModeloSqlServer implements IModelo {
	private IConector conector;
	
	/**
	 * Constructor
	 * 
	 * @param servidor
	 * @param baseDatos
	 * @param usuario
	 * @param clave
	 */
	public ModeloSqlServer(IConector conector) {
		super();
		
		this.conector = conector;
	}

	/**
	 * Obtiene la información de la base de datos y construye e interpreta su estructura
	 */
	public void preparar() {
		Statement sentencia = null;
	    ResultSet registros = null;
	    
        String SQL = "SELECT " + 
        			"TABLE_SCHEMA AS Esquema, "+
					"TABLE_NAME AS Tabla, "+
					"COLUMN_NAME AS Columna, "+
					"DATA_TYPE AS Tipo, "+
					"IS_NULLABLE AS Nulos, "+
					"CHARACTER_MAXIMUM_LENGTH AS LongitudMaxima, "+
					"NUMERIC_PRECISION AS [Precision], "+
					"COLUMN_DEFAULT AS Predeterminado "+
				"FROM "+ 
					"information_schema.columns "+
				"ORDER BY "+
					"TABLE_SCHEMA,"+
					"TABLE_NAME,"+
					"ORDINAL_POSITION";
        
        try {
        	this.conector.conectar();
			sentencia = this.conector.getConexion().createStatement();
			registros = sentencia.executeQuery(SQL);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
	         if (registros != null) try { registros.close(); } catch(Exception e) {}
	         if (sentencia != null) try { sentencia.close(); } catch(Exception e) {}
        }
	}
}
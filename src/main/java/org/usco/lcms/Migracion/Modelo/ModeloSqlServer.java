package org.usco.lcms.Migracion.Modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.usco.lcms.Migracion.Conectores.IConector;

/**
 * Manejador del modelo de la base de dato SQL Server
 * 
 * @author Jonathan Tovar Sanmiguel
 */
public class ModeloSqlServer implements IModelo {
	private IConector conector;
	private Map<String, EsquemaSqlServer> listaEsquemas; 
	
	/**
	 * Constructor
	 * 
	 * @param conector
	 */
	public ModeloSqlServer(IConector conector) {
		super();
		
		this.conector = conector;
	}

	/**
	 * Obtiene la información de la base de datos, interpreta y construye su estructura
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
			//
			String nombreEsquema, nombreTabla, nombreColumna;
			EsquemaSqlServer esquema;
			TablaSqlServer tabla;
			ColumnaSqlServer columna;
			Map<String, ITabla> listaTablas;
			Map<String, IColumna> listaColumnas;
			this.listaEsquemas = new HashMap<String, EsquemaSqlServer>();
			
			while (registros.next()) {
				// Obtenemos datos del registro
				nombreEsquema = registros.getString("Esquema");
				nombreTabla = registros.getString("Tabla");
				nombreColumna = registros.getString("Columna");
				// Creamos u obtenemos esquema
				if (!this.listaEsquemas.containsKey(nombreEsquema)) {
					esquema = new EsquemaSqlServer();
					esquema.setNombre(nombreEsquema);
					this.listaEsquemas.put(nombreEsquema, esquema);
				} else {
					esquema = this.listaEsquemas.get(nombreEsquema);
				}
				// Creamos u obtenemos tabla
				listaTablas = esquema.getTablas();
				if (!listaTablas.containsKey(nombreTabla)) {
					tabla = new TablaSqlServer();
					tabla.setNombre(nombreTabla);
					listaTablas.put(nombreTabla, tabla);
				} else {
					tabla = (TablaSqlServer)listaTablas.get(nombreTabla);
				}
				// Creamos u obtenemos columna
				listaColumnas = tabla.getColumnas();
				if (!listaColumnas.containsKey(nombreColumna)) {
					columna = new ColumnaSqlServer();
					columna.setNombre(nombreColumna);
					listaColumnas.put(nombreColumna, columna);
				} else {
					columna = (ColumnaSqlServer)listaColumnas.get(nombreColumna);
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
	         if (registros != null) try { registros.close(); } catch(Exception e) {}
	         if (sentencia != null) try { sentencia.close(); } catch(Exception e) {}
        }
	}
}
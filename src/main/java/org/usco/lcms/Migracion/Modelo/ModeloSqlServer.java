package org.usco.lcms.Migracion.Modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.usco.lcms.Migracion.TiposModeloBaseDatos;
import org.usco.lcms.Migracion.Conectores.ConectorSqlServer;
import org.usco.lcms.Migracion.Conectores.IConector;

/**
 * Manejador del modelo de la base de datos SQL Server
 * 
 * @author Jonathan Tovar Sanmiguel
 */
public class ModeloSqlServer implements IModelo {
	private IConector conector;
	private Map<String, IEsquema> listaEsquemas; 
	
	/**
	 * Constructor
	 * 
	 * @param conector
	 */
	public ModeloSqlServer(ConectorSqlServer conector) {
		super();
		
		this.conector = conector;
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
		return TiposModeloBaseDatos.SQLSERVER;
	}
	
	/**
	 * Migra el modelo actual de base de datos a uno de destino
	 * 
	 * @param modelo Modelo de la base de datos de destino
	 */
	public void migrarDesde(IModelo modelo) {
		Statement sentencia = null;
		
		try {
        	this.conector.conectar();
			sentencia = this.conector.getConexion().createStatement();
			sentencia.execute("SET DATEFORMAT DMY");
			sentencia.execute("CREATE DATABASE "+ this.conector.getBaseDatos());
			
			EsquemaSqlServer esquema;
			Map<String, IEsquema> listaEsquemasReferencia = modelo.getEsquemas();
			for(IEsquema esquemaReferencia: listaEsquemasReferencia.values()) {
				esquema = new EsquemaSqlServer(this, esquemaReferencia);
				esquema.migrar();
			}
        } catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
	         if (sentencia != null) try { sentencia.close(); } catch(Exception e) {}
        }
	}
	
	/**
	 * Construye y devuelve la cadena SQL para construir el modelo
	 * 
	 * @return StringBuilder Cadena SQL
	 */
	public StringBuilder obtenerSql() {
		StringBuilder retorno = new StringBuilder();
		
		if (this.listaEsquemas != null) {
			retorno.append("SET DATEFORMAT DMY;\n");
			retorno.append("GO\n\n");
			retorno.append("CREATE DATABASE "+ this.conector.getBaseDatos() +";\n");
			retorno.append("GO\n\n");
			
			for(IEsquema esquema: this.listaEsquemas.values()) {
				retorno.append(esquema.obtenerSql());
			}
		}
		
		return retorno;
	}

	/**
	 * Obtiene la información de la base de datos, interpreta y construye su estructura
	 */
	public void preparar() {
		Statement sentencia = null;
	    ResultSet registros = null;
		//
		String nombreEsquema, nombreTabla, nombreColumna, cadenaTipo, cadenaNulos, cadenaLongitudMaxima, cadenaPrecision, cadenaPredeterminado;
		String nombreLlave;
		Boolean esPrimaria, esDescendente;
		Integer longitudMaxima, precision;
		IEsquema esquema;
		TablaSqlServer tabla;
		ColumnaSqlServer columna;
		LlaveSqlServer llave;
		Map<String, ITabla> listaTablas;
		Map<String, IColumna> listaColumnas;
		Map<String, ILlave> listaLlaves;
		this.listaEsquemas = new HashMap<String, IEsquema>();
	    
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
			while (registros.next()) {
				// Obtenemos datos del registro
				nombreEsquema = registros.getString("Esquema");
				nombreTabla = registros.getString("Tabla");
				nombreColumna = registros.getString("Columna");
				cadenaTipo = registros.getString("Tipo");
				cadenaNulos = registros.getString("Nulos");
				cadenaLongitudMaxima = registros.getString("LongitudMaxima");
				longitudMaxima = null;
				if (cadenaLongitudMaxima != null && !cadenaLongitudMaxima.isEmpty()) {
					longitudMaxima = Integer.parseInt(cadenaLongitudMaxima);
				}
				cadenaPrecision = registros.getString("Precision");
				precision = null;
				if (cadenaPrecision != null && !cadenaPrecision.isEmpty()) {
					precision = Integer.parseInt(cadenaPrecision);
				}
				cadenaPredeterminado = registros.getString("Predeterminado");
				// Creamos u obtenemos esquema
				if (!this.listaEsquemas.containsKey(nombreEsquema)) {
					esquema = new EsquemaSqlServer(this);
					esquema.setNombre(nombreEsquema);
					this.listaEsquemas.put(nombreEsquema, esquema);
				} else {
					esquema = this.listaEsquemas.get(nombreEsquema);
				}
				// Creamos u obtenemos tabla
				listaTablas = esquema.getTablas();
				if (!listaTablas.containsKey(nombreTabla)) {
					tabla = new TablaSqlServer(this, nombreEsquema, nombreTabla);
					listaTablas.put(nombreTabla, tabla);
				} else {
					tabla = (TablaSqlServer)listaTablas.get(nombreTabla);
				}
				// Creamos columna
				listaColumnas = tabla.getColumnas();
				columna = new ColumnaSqlServer();
				columna.setNombre(nombreColumna);
				columna.setTipo(cadenaTipo);
				columna.setNulos(cadenaNulos.equals("YES"));
				columna.setLongitudMaxima(longitudMaxima);
				columna.setPrecision(precision);
				columna.setPredeterminado(cadenaPredeterminado);
				listaColumnas.put(nombreColumna, columna);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
	         if (registros != null) try { registros.close(); } catch(Exception e) {}
	         if (sentencia != null) try { sentencia.close(); } catch(Exception e) {}
        }
        // Obtenemos las llaves primarias
        SQL = "SELECT " +
        		"s.name AS Esquema, "+ 
    			"t.name AS Tabla, "+
				"i.name AS Llave, "+
				"c.name AS Columna, "+
				"i.is_primary_key AS Primaria, "+
				"ic.is_descending_key AS Descendente "+
			"FROM "+ 
				"sys.indexes AS i "+
				"INNER JOIN sys.index_columns AS ic ON "+
					"i.object_id = ic.object_id AND "+
					"i.index_id = ic.index_id "+
				"INNER JOIN sys.columns AS c ON "+
					"i.object_id = c.object_id AND "+
					"c.column_id = ic.column_id "+
				"INNER JOIN sys.tables AS t ON "+
					"t.object_id = i.object_id "+
				"INNER JOIN sys.schemas AS s ON "+
					"s.schema_id = t.schema_id "+
			"ORDER BY "+
				"s.name,"+
				"t.name,"+
				"i.name,"+
				"ic.index_column_id";
        try {
        	this.conector.conectar();
			sentencia = this.conector.getConexion().createStatement();
			registros = sentencia.executeQuery(SQL);
			while (registros.next()) {
				// Obtenemos datos del registro
				nombreLlave = registros.getString("Llave");
				nombreEsquema = registros.getString("Esquema");
				nombreTabla = registros.getString("Tabla");
				nombreColumna = registros.getString("Columna");
				esPrimaria = registros.getBoolean("Primaria");
				esDescendente = registros.getBoolean("Descendente");
				// Obtenemos la tabla
				esquema = this.listaEsquemas.get(nombreEsquema);
				listaTablas = esquema.getTablas();
				tabla = (TablaSqlServer)listaTablas.get(nombreTabla);
				listaLlaves = tabla.getLlaves();
				if (!listaLlaves.containsKey(nombreLlave)) {
					llave = new LlaveSqlServer(nombreLlave, esPrimaria);
					listaLlaves.put(nombreLlave, llave);
				} else {
					llave = (LlaveSqlServer)listaLlaves.get(nombreLlave);
				}
				llave.getColumnas().put(nombreColumna, esDescendente);
			}
        } catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
	         if (registros != null) try { registros.close(); } catch(Exception e) {}
	         if (sentencia != null) try { sentencia.close(); } catch(Exception e) {}
        }
	}
}
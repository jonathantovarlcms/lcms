package org.usco.lcms.migracion.modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.usco.lcms.migracion.constantes.TiposDatosPostgres;
import org.usco.lcms.migracion.constantes.TiposDatosSqlServer;
import org.usco.lcms.migracion.constantes.TiposModeloBaseDatos;

/**
 * Manejador de tablas para el modelo de la base de datos SQL Server
 * 
 * @author Jonathan Tovar Sanmiguel
 */
public class TablaSqlServer implements ITabla {
	private IModelo modelo;
	private String comentarios;
	private String esquema;
	private String nombre;
	private Map<String, IColumna> listaColumnas;
	private Map<String, ILlave> listaLlaves;
	
	/**
	 * Constructor
	 * 
	 * @param modelo Modelo de la base de datos
	 * @param esquema Nombre del esquema de la tabla
	 * @params nombre Nombre de la tabla
	 */
	public TablaSqlServer(IModelo modelo, String esquema, String nombre) {
		super();
		
		this.modelo = modelo;
		this.esquema = esquema;
		this.nombre = nombre;
		
		this.listaColumnas = new HashMap<String, IColumna>();
		this.listaLlaves = new HashMap<String, ILlave>();
	}
	
	/**
	 * Constructor
	 * 
	 * @param modelo Modelo de la base de datos
	 * @param tabla Tabla de referencia
	 */
	public TablaSqlServer(IModelo modelo, ITabla tabla) {
		super();
		
		this.modelo = modelo;
		this.esquema = tabla.getEsquema();
		this.nombre = tabla.getNombre();
		
		this.listaColumnas = new HashMap<String, IColumna>();
		this.listaLlaves = new HashMap<String, ILlave>();
		
		String tipoModelo = modelo.getTipo();
		String tipo;
		IColumna columna, columnaReferencia;
		Map<String, IColumna> listaColumnasReferencia = tabla.getColumnas();
		for(String nombreColumnaReferencia : listaColumnasReferencia.keySet()) {
			columnaReferencia = listaColumnasReferencia.get(nombreColumnaReferencia);
			
			tipo = columnaReferencia.getTipo();
			if (tipoModelo.equals(TiposModeloBaseDatos.POSTGRES)) {
				if (tipo.equals(TiposDatosPostgres.BIGINT)) {
					tipo = TiposDatosSqlServer.BIGINT;
				} else if (tipo.equals(TiposDatosPostgres.INTEGER)) {
					tipo = TiposDatosSqlServer.INT;
				} else if (tipo.equals(TiposDatosPostgres.CHARACTER_VARYING)) {
					tipo = TiposDatosSqlServer.VARCHAR;
				} else if (tipo.equals(TiposDatosPostgres.CHARACTER)) {
					tipo = TiposDatosSqlServer.CHAR;
				} else if (tipo.equals(TiposDatosPostgres.FLOAT)) {
					tipo = TiposDatosSqlServer.FLOAT;
				} else if (tipo.equals(TiposDatosPostgres.TIMESTAMP)) {
					tipo = TiposDatosSqlServer.DATETIME;
				} else if (tipo.equals(TiposDatosPostgres.BIT)) {
					tipo = TiposDatosSqlServer.BIT;
				} else if (tipo.equals(TiposDatosPostgres.MONEY)) {
					tipo = TiposDatosSqlServer.MONEY;
				} else if (tipo.equals(TiposDatosPostgres.DATE)) {
					tipo = TiposDatosSqlServer.DATE;
				} else if (tipo.equals(TiposDatosPostgres.TEXT)) {
					tipo = TiposDatosSqlServer.TEXT;
				} else if (tipo.equals(TiposDatosPostgres.BYTEA)) {
					tipo = TiposDatosSqlServer.VARBINARY;
				} else if (tipo.equals(TiposDatosPostgres.SMALLINT)) {
					tipo = TiposDatosSqlServer.SMALLDATETIME;
				} else {
					System.out.println("Tipo de dato no definido: " + tipo);
				}
			}
			
			columna = new ColumnaSqlServer();
			columna.setLongitudMaxima(columnaReferencia.getLongitudMaxima());
			columna.setNombre(columnaReferencia.getNombre());
			columna.setNulos(columnaReferencia.getNulos());
			columna.setPrecision(columnaReferencia.getPrecision());
			columna.setPredeterminado(columnaReferencia.getPredeterminado());
			columna.setTipo(tipo);
			this.listaColumnas.put(nombreColumnaReferencia, columna);
		}
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
		return this.nombre;
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
		return this.listaColumnas;
	}
	
	/**
	 * @return Devuelve la lista de llaves de la tabla
	 */
	public Map<String, ILlave> getLlaves() {
		return this.listaLlaves;
	}
	
	/**
	 * @return Cadena SQL solo para la creación de la tabla
	 */
	private String obtenerSqlCreacion() {
		StringBuilder retorno = new StringBuilder();
		StringBuilder cadenaLlave = new StringBuilder();
		ArrayList<String> cadenasColumnas = new ArrayList<String>();
		ArrayList<String> cadenasColumnasLlave;
		Map<String, Boolean> listaColumnasLlave;
		
		for(IColumna columna: this.listaColumnas.values()) {
			cadenasColumnas.add("    "+columna.obtenerSql());			
		}
		for(ILlave llave: this.listaLlaves.values()) {
			cadenasColumnasLlave = new ArrayList<String>();
			listaColumnasLlave = llave.getColumnas();
			
			if (llave.getEsPrimaria()) {
				for(String nombreColumna: listaColumnasLlave.keySet()) {
					if (listaColumnasLlave.get(nombreColumna)) {
						cadenasColumnasLlave.add("    ["+nombreColumna+"] ASC");
					} else {
						cadenasColumnasLlave.add("    ["+nombreColumna+"] DESC");
					}
				}
				cadenaLlave.append("CONSTRAINT ["+llave.getNombre()+"] PRIMAY KEY CLUSTERED;\n(\n");
				cadenaLlave.append(String.join(",\n", cadenasColumnasLlave));
				cadenaLlave.append("\n)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]");
				cadenasColumnas.add(cadenaLlave.toString());
				break;
			}
		}
		
		retorno.append("CREATE TABLE ["+this.esquema+"].["+this.nombre+"](\n");
		retorno.append(String.join(",\n", cadenasColumnas));
		retorno.append("\n) ON [PRIMARY]\n");
		
		return retorno.toString();
	}
	
	/**
	 * Realiza la migracion al modelo configurado
	 */
	public void migrar() {
		this.migrarTabla();
	}
	
	/**
	 * Realiza la migracion al modelo configurado
	 */
	public void migrarTabla() {
		String SQL;
		Connection conexion = null;
		Statement sentencia = null;
		
		try {
			conexion = this.modelo.getConector().conectar();
			sentencia = conexion.createStatement();
			sentencia.execute(this.obtenerSqlCreacion());
			
			for(IColumna columna: this.listaColumnas.values()) {
				String nombreColumna = columna.getNombre();
				String predeterminado = columna.getPredeterminado();
				
				if (predeterminado != null) {
					SQL = "ALTER TABLE ["+this.esquema+"].["+this.nombre+"] "+
						"ADD CONSTRAINT [DF_"+this.nombre+"_"+nombreColumna+"] "+
						"DEFAULT "+predeterminado+" FOR ["+nombreColumna+"]";
					sentencia.execute(SQL);
				}
			}
        } catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
	         if (sentencia != null) try { sentencia.close(); } catch(Exception e) {}
	         if (conexion != null) try { conexion.close(); } catch(Exception e) {}
        }
	}
	
	/**
	 * Construye y devuelve la cadena SQL para construir la tabla
	 * 
	 * @return StringBuilder Cadena SQL
	 */
	public StringBuilder obtenerSql() {
		StringBuilder retorno = new StringBuilder();
		
		retorno.append(this.obtenerSqlCreacion());
		retorno.append("GO\n\n");
		
		for(IColumna columna: this.listaColumnas.values()) {
			String nombreColumna = columna.getNombre();
			String predeterminado = columna.getPredeterminado();
			
			if (predeterminado != null) {
				retorno.append("ALTER TABLE ["+this.esquema+"].["+this.nombre+"] ");
				retorno.append("ADD CONSTRAINT [DF_"+this.nombre+"_"+nombreColumna+"] ");
				retorno.append("DEFAULT "+predeterminado+" FOR ["+nombreColumna+"]\n");
				retorno.append("GO\n\n");
			}
		}
		
		return retorno;
	}
}
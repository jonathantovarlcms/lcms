package org.usco.lcms.migracion.conectores;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.usco.lcms.migracion.constantes.TiposModeloBaseDatos;
import org.usco.lcms.migracion.modelo.IModelo;
import org.usco.lcms.migracion.modelo.ModeloSqlServer;

/**
 * Maneja la conexión a una base de datos SQL Server
 * 
 * @author Jonathan Tovar Sanmiguel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConectorSqlServer implements IConector {
	private IModelo modelo;
	private String ID;
	private String servidor;
	private String baseDatos;
	private String usuario;
	@JsonIgnore 
	private String clave;

	/**
	 * Constructor
	 * 
	 * @param servidor
	 * @param baseDatos
	 * @param usuario
	 * @param clave
	 */
	public ConectorSqlServer(String servidor, String baseDatos, String usuario, String clave) {
		super();
		
		try {
			String digServidor = servidor + ":" + baseDatos + ":" + usuario + ":" + clave;
			MessageDigest cMD5 = MessageDigest.getInstance("MD5");
			this.ID = (new BigInteger(1, cMD5.digest(digServidor.getBytes()))).toString(16);
			while (this.ID.length() < 32) {
				this.ID = "0" + this.ID;
            }
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		this.servidor = servidor;
		this.baseDatos = baseDatos;
		this.usuario = usuario;
		this.clave = clave;
	}
	
	/**
	 * @return El identificador unico de la conexion
	 */
	public String getID() {
		return this.ID;
	}
	
	/**
	 * @return El nombre de la base de datos
	 */
	public String getBaseDatos() {
		return this.baseDatos;
	}

	/**
	 * @param baseDatos Nombre de la base de datos
	 */
	public void setBaseDatos(String baseDatos) {
		this.baseDatos = baseDatos;
	}
	
	/**
	 * @return Clave de conexión a la base de datos
	 */
	public String getClave() {
		return this.clave;
	}

	/**
	 * @param clave Clave de conexión a la base de datos
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * @return La dirección del servidor de base de datos
	 */
	public String getServidor() {
		return this.servidor;
	}

	/**
	 * @param servidor Dirección del servidor de base de datos
	 */
	public void setServidor(String servidor) {
		this.servidor = servidor;
	}
	
	/**
	 * @return Devuelve el tipo de modelo de base de datos
	 */
	@JsonProperty
	public String getTipo() {
		return TiposModeloBaseDatos.SQLSERVER;
	}

	/**
	 * @return Nombre de usuario de conexión a la base de datos
	 */
	public String getUsuario() {
		return this.usuario;
	}

	/**
	 * @param usuario Nombre de usuario de conexión a la base de datos
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * Realiza conexión a la base de datos
	 * 
	 * @return Conexión a la base de datos
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public Connection conectar() throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		
		String CadenaConexion = "jdbc:sqlserver://"+this.getServidor()+":1433;databaseName=" + this.getBaseDatos() + ";CharacterSet=UTF-8";
		return DriverManager.getConnection(CadenaConexion, this.getUsuario(), this.getClave());
	}
	
	/**
	 * Realiza una prueba de la conexión a la base de datos
	 * 
	 * @return Indicador de prueba satisfactoria
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Boolean probarConexion() throws ClassNotFoundException, SQLException {
		Statement sentencia;
	    ResultSet registros;
	    
		Connection conexion = this.conectar();
		sentencia = conexion.createStatement();
		registros = sentencia.executeQuery("SELECT 1+1 AS [Conteo]");
		registros.next();
		Integer conteo = registros.getInt("Conteo");
		
		try { registros.close(); } catch(Exception e) {}
		try { sentencia.close(); } catch(Exception e) {}
		try { conexion.close(); } catch(Exception e) {}
		
		return conteo.equals(2);
	}
	
	/**
	 * Deuelve el objeto del modelo a una base de datos SQL Server
	 *  
	 * @return Objeto del modelo de la base de datos
	 */
	public IModelo obtenerModelo() {
		if (this.modelo==null) {
			this.modelo = new ModeloSqlServer(this);
		}
		return this.modelo;
	}
}

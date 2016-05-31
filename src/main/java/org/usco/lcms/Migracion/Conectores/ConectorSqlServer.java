package org.usco.lcms.Migracion.Conectores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.usco.lcms.Migracion.Modelo.IModelo;
import org.usco.lcms.Migracion.Modelo.ModeloSqlServer;

/**
 * Maneja la conexión a una base de datos SQL Server
 * 
 * @author Jonathan Tovar Sanmiguel
 */
public class ConectorSqlServer implements IConector {
	private Connection conexion;
	private IModelo modelo;
	private String servidor;	
	private String baseDatos;
	private String usuario;
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
		this.setServidor(servidor);;
		this.setBaseDatos(baseDatos);
		this.setUsuario(usuario);
		this.setClave(clave);
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
	 * @return Devuelve el objeto de conexion a la base de datos
	 */
	public Connection getConexion() {
		return this.conexion;
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
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public void conectar() throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		
		if (this.conexion == null) {
			String CadenaConexion = "jdbc:sqlserver://"+this.getServidor()+":1433;databaseName=" + this.getBaseDatos() + ";CharacterSet=UTF-8";
			this.conexion = DriverManager.getConnection(CadenaConexion, this.getUsuario(), this.getClave());
		}
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

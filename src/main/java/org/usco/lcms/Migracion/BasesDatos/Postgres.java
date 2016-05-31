package org.usco.lcms.Migracion.BasesDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Maneja la conexión a una base de datos Postgres
 * 
 * @author Jonathan Tovar Sanmiguel
 */
public class Postgres implements IBaseDatos {
	private Connection conexion;
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
	public Postgres(String servidor, String baseDatos, String usuario, String clave) {
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
		Class.forName("org.postgresql.Driver");
		
		if (this.conexion == null) {
			String CadenaConexion = "jdbc:postgresql://"+this.getServidor()+":5432/" + this.getBaseDatos();
			this.conexion = DriverManager.getConnection(CadenaConexion, this.getUsuario(), this.getClave());
		}
	}
}

package org.usco.lcms.migracion.definiciones;

/**
 * Clase contenedora de la información de prueba de conexión a base de datos 
 * 
 * @author Jonathan Tovar Sanmiguel
 *
 */
public class ConectarServidor {
	private String id;
	private Boolean origen;
	private Boolean conectado;
	private String mensaje;
	
	/**
	 * @return El ID de la conexión
	 */
	public String getID() {
		return this.id;
	}
	/**
	 * @param id El ID de la conexión
	 */
	public void setID(String id) {
		this.id = id;
	}
	/**
	 * @return Indicador de servidor de origen
	 */
	public Boolean getOrigen() {
		return this.origen;
	}
	/**
	 * @param origen Indicador de servidor de origen
	 */
	public void setOrigen(Boolean origen) {
		this.origen = origen;
	}
	/**
	 * @return Indicador de conectado
	 */
	public Boolean getConectado() {
		return this.conectado;
	}
	/**
	 * @param conectado Indicador de conectado
	 */
	public void setConectado(Boolean conectado) {
		this.conectado = conectado;
	}
	/**
	 * @return Mensaje de error 
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje Mensaje de error 
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}

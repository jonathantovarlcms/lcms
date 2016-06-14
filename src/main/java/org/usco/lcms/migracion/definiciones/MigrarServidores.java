package org.usco.lcms.migracion.definiciones;

import java.util.ArrayList;

/**
 * Clase contenedora de la información de migración de base de datos 
 * 
 * @author Jonathan Tovar Sanmiguel
 *
 */
public class MigrarServidores {
	private ConectarServidor servidorOrigen;
	private ArrayList<ConectarServidor> servidoresDestino;
	/**
	 * @return Servidor de origen
	 */
	public ConectarServidor getServidorOrigen() {
		return servidorOrigen;
	}
	/**
	 * @param servidorOrigen Servidor de origen
	 */
	public void setServidorOrigen(ConectarServidor servidorOrigen) {
		this.servidorOrigen = servidorOrigen;
	}
	/**
	 * @return Lista de servidores de destino
	 */
	public ArrayList<ConectarServidor> getServidoresDestino() {
		return servidoresDestino;
	}
	/**
	 * @param servidoresDestino Lista de servidores de destino
	 */
	public void setServidoresDestino(ArrayList<ConectarServidor> servidoresDestino) {
		this.servidoresDestino = servidoresDestino;
	}
}

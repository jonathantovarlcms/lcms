package org.usco.lcms.migracion.conectores;

import java.sql.Connection;
import java.sql.SQLException;

import org.usco.lcms.migracion.modelo.IModelo;

public interface IConector {
	Connection conectar() throws ClassNotFoundException, SQLException;
	String getID();
	String getBaseDatos();
	void setBaseDatos(String basedatos);
	IModelo obtenerModelo();
	Boolean probarConexion() throws ClassNotFoundException, SQLException;
}

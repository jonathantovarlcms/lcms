package org.usco.lcms.Migracion.Conectores;

import java.sql.Connection;
import java.sql.SQLException;

import org.usco.lcms.Migracion.Modelo.IModelo;

public interface IConector {
	void conectar() throws ClassNotFoundException, SQLException;
	String getBaseDatos();
	void setBaseDatos(String basedatos);
	IModelo obtenerModelo();
	Connection getConexion();
}

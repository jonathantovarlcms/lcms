package org.usco.lcms.Migracion.BasesDatos;

import java.sql.SQLException;

public interface IBaseDatos {
	void conectar() throws ClassNotFoundException, SQLException;
}

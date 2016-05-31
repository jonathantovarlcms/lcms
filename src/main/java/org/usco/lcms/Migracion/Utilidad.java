package org.usco.lcms.Migracion;

import org.usco.lcms.Migracion.Conectores.ConectorSqlServer;
import org.usco.lcms.Migracion.Modelo.IModelo;;

public class Utilidad {

	public static void migrar() {
		//ConectorPostgres cPostgres = new ConectorPostgres("localhost", "lcms", "postgres", "password");
		ConectorSqlServer cSqlServer = new ConectorSqlServer("localhost", "lcms", "sa", "password");
		IModelo mSqlServer = cSqlServer.obtenerModelo();
		mSqlServer.preparar();
	}
}

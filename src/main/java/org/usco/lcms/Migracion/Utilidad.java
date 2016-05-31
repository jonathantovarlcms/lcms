package org.usco.lcms.Migracion;

import org.usco.lcms.Migracion.Conectores.ConectorPostgres;
import org.usco.lcms.Migracion.Conectores.ConectorSqlServer;
import org.usco.lcms.Migracion.Modelo.ModeloPostgres;
import org.usco.lcms.Migracion.Modelo.ModeloSqlServer;

public class Utilidad {

	public static void migrar() {
		ConectorPostgres cPostgres = new ConectorPostgres("localhost", "lcms", "postgres", "password");
		ConectorSqlServer cSqlServer = new ConectorSqlServer("localhost", "lcms", "sa", "password");
		ModeloSqlServer mSqlServer = (ModeloSqlServer)cSqlServer.obtenerModelo();
		ModeloPostgres mPostgres = (ModeloPostgres)cPostgres.obtenerModelo();
		mSqlServer.preparar();
	}
}

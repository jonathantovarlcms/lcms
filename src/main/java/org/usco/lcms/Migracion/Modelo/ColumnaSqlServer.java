package org.usco.lcms.Migracion.Modelo;

public class ColumnaSqlServer implements IColumna {
	private String nombre;
	
	/**
	 * @return Nombre de la tabla
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre Nombre de la tabla
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}

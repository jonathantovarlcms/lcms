package org.usco.lcms.Migracion.Modelo;

public interface IColumna {
	String getNombre();
	void setNombre(String nombre);
	String getTipo();
	void setTipo(String tipo);
	Boolean getNulos();
	void setNulos(Boolean nulos);
	Integer getLongitudMaxima();
	void setLongitudMaxima(Integer longitudMaxima);
	Integer getPrecision();
	void setPrecision(Integer precision);
	String getPredeterminado();
	void setPredeterminado(String predeterminado);
	StringBuilder obtenerSql();
}

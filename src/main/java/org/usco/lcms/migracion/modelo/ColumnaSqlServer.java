package org.usco.lcms.migracion.modelo;

public class ColumnaSqlServer implements IColumna {
	private String nombre;
	private String tipo;
	private Boolean nulos;
	private Integer longitudMaxima;
	private Integer precision;
	private String predeterminado;
	
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

	/**
	 * @return El tipo de columna de SQL Server
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo El tipo de columna de SQL Server a establecer
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the nulos
	 */
	public Boolean getNulos() {
		return nulos;
	}

	/**
	 * @param nulos the nulos to set
	 */
	public void setNulos(Boolean nulos) {
		this.nulos = nulos;
	}

	/**
	 * @return the longitudMaxima
	 */
	public Integer getLongitudMaxima() {
		return this.longitudMaxima;
	}

	/**
	 * @param longitudMaxima the longitudMaxima to set
	 */
	public void setLongitudMaxima(Integer longitudMaxima) {
		this.longitudMaxima = longitudMaxima;
	}

	/**
	 * @return the precision
	 */
	public Integer getPrecision() {
		return precision;
	}

	/**
	 * @param precision the precision to set
	 */
	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	/**
	 * @return the predeterminado
	 */
	public String getPredeterminado() {
		return predeterminado;
	}

	/**
	 * @param predeterminado the predeterminado to set
	 */
	public void setPredeterminado(String predeterminado) {
		this.predeterminado = predeterminado;
	}
	
	/**
	 * Construye y devuelve la cadena SQL para construir la columna
	 * 
	 * @return StringBuilder Cadena SQL
	 */
	public StringBuilder obtenerSql() {
		StringBuilder retorno = new StringBuilder();
		
		retorno.append("["+this.nombre+"] "+this.tipo);
		if (this.tipo.equals("varchar") || this.tipo.equals("nvarchar") || this.tipo.equals("char") || this.tipo.equals("nchar")) {
			retorno.append("("+this.longitudMaxima.toString()+")");
		} else if (this.tipo.equals("float")) {
			retorno.append("("+this.precision.toString()+")");
		}
		if (this.nulos) {
			retorno.append(" NULL");
		} else {
			retorno.append(" NOT NULL");			
		}
		
		return retorno;
	}
}

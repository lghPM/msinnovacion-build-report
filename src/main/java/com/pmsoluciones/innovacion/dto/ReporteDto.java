package com.pmsoluciones.innovacion.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.Value;

/**
 * DTO for {@link com.pmsoluciones.innovacion.entity.PincAlcanceMetrica}
 */
@Data
public class ReporteDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Long id;
	String desAlcanceMetrica;
	String refUsuarioAlta;
	String refUsuarioBaja;
	String refUsuarioModifica;
	Date stpAlta;
	Date stpBaja;
	Date stpActualizacion;
	Boolean indActivo;
}
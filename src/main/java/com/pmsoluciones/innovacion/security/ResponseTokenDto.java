package com.pmsoluciones.innovacion.security;

import lombok.Data;

@Data
public class ResponseTokenDto {

	private String mensaje;
	private InfoTokenDto respuesta;
	private Boolean estatus;
	private Integer code;
}

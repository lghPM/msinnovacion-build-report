package com.pmsoluciones.innovacion.security;

import lombok.Data;

@Data
public class RespuestaTokenDto {
	
	private String mensaje;
	private InformacionTokenDto respuesta;
	private Boolean estatus;
	private Integer code;

}

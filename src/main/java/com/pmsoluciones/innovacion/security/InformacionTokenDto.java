package com.pmsoluciones.innovacion.security;

import java.util.List;

import lombok.Data;

@Data
public class InformacionTokenDto {
	
	private String sub;
	private Long iat;
	private UsuarioTokenDto Usuario;
	private Long exp;
	private List<String> authorities;

}

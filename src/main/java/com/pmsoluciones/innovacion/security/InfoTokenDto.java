package com.pmsoluciones.innovacion.security;

import java.util.List;

import lombok.Data;

@Data
public class InfoTokenDto {

	private String sub;
	private Long iat;
	private UsuarioLdapDto Usuario;
	private Long exp;
	private List<String> authorities;
}

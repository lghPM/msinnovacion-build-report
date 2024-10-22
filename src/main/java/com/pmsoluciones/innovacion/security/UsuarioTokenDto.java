package com.pmsoluciones.innovacion.security;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioTokenDto {
	
	private Long idUsuario;
	private String nombreCompleto;
	private String usuarioDominio;
	private String email;
	private String description;
	private String samaccountName;
	private Long idRol;
	private Long idUsuarioRol;
	private String desRol;
	private List<Menu> accesos;
	

}

package com.pmsoluciones.innovacion.security;

import java.util.List;

import lombok.Data;

@Data
public class Submenu {
	
	private Long idSubmenu;
	private String desSubmenu;
	private String refUrl;
	private List<Long> idPermiso;

}

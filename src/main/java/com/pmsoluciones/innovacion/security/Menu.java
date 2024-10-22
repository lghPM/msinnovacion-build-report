package com.pmsoluciones.innovacion.security;

import java.util.List;

import lombok.Data;

@Data
public class Menu {
	
	private long idMenu;
	private String desMenu;
	private String refIcono;
	private List<Submenu> submenu;

}

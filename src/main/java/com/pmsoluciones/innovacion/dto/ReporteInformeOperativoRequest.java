package com.pmsoluciones.innovacion.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ReporteInformeOperativoRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8287133385974663780L;
	/**
	 * 
	 */
	private String fecInicio;
	private String fecFin;
	private String idVistaMetrica;
	private String cveInforme;
	private String nomInforme;
	private String refUrlRepos;
	// LISTA DE PROYECTOS CON UNA LISTA DE METRICAS
	private List<ProyectoDto> proyectos;

}
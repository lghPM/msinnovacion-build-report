package com.pmsoluciones.innovacion.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.pmsoluciones.innovacion.entity.PintProyecto}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProyectoDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4448351207337026748L;
	private Long id;
    private String cveProyecto;
    private String nomProyecto;
    private  List<MetricaIndicadorDto>metricaIndicadores;
	private List<MetricasDto> lstAcr;
	private List<MetricasDto> lstPpb;

}
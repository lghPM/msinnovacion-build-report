package com.pmsoluciones.innovacion.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link PintPmMetricaDef}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class MetricasDto implements Serializable {
	/**
	 *  
	 */
	private static final long serialVersionUID = -710715724312741934L;
	private Long id;
	private String cveMetrica;
	private String nomMetrica;
	private String fechaDoc;
	private String nomDoc;
	private Long idProyecto;
	private String cveProyecto;
	private String desLcl;
	private String desMedia;
	private String desUcl;

}
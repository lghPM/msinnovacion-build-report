package com.pmsoluciones.innovacion.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link PintPmMetricaDef}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricaIndicadorDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -710715724312741934L;
	private Long id;
	private Long idPmAsignUsuarioMetrica;
	private String cveMetrica;
	private String nomMetrica;
	private String nomVistaMetrica;
	private List<FechaProgramadaDto> fechasProgramadas;

}
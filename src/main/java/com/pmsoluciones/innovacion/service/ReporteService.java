/**
 * 
 */
package com.pmsoluciones.innovacion.service;

import org.springframework.http.ResponseEntity;

import com.pmsoluciones.innovacion.dto.ReporteInformeOperativoRequest;

/**
 * 
 */
public interface ReporteService {

	ResponseEntity<?> get(ReporteInformeOperativoRequest reporteInformeOperativoRequest, String token);

}

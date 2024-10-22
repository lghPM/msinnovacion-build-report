/**
 * 
 */
package com.pmsoluciones.innovacion.service;

import org.springframework.http.ResponseEntity;

/**
 * 
 */
public interface ReporteService {

	ResponseEntity<?> get(Long idAlcanceMetrica);

}

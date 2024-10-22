/**
 * 
 */
package com.pmsoluciones.innovacion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmsoluciones.innovacion.dto.ReporteInformeOperativoRequest;
import com.pmsoluciones.innovacion.service.ReporteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * 
 */
@Log4j2
@RequestMapping("/v1")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReporteController {

	private final ReporteService service;

	@PostMapping(value = "/reporte1")
	public ResponseEntity<?> get(@RequestBody ReporteInformeOperativoRequest reporteInformeOperativoRequest,
			@RequestHeader(required = false, value = "Authorization") String token) {
		log.info("get reporteInformeOperativoRequest: {}", reporteInformeOperativoRequest);
		return service.get(reporteInformeOperativoRequest, token);
	}
}

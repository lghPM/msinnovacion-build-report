/**
 * 
 */
package com.pmsoluciones.innovacion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping(value = "/reporte1")
	public ResponseEntity<?> get(@RequestParam(required = false, value = "id") Long id) {
		log.info("get AlcanceMetrica: {}", id);
		return service.get(id);
	}
}

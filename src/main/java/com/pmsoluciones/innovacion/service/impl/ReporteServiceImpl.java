/**
 * 
 */
package com.pmsoluciones.innovacion.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pmsoluciones.innovacion.client.ClientRest;
import com.pmsoluciones.innovacion.dto.ReporteInformeOperativoRequest;
import com.pmsoluciones.innovacion.service.ReporteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * 
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

	private final ClientRest clientRest;

	@Transactional
	@Override
	public ResponseEntity<?> get(ReporteInformeOperativoRequest reporteInformeOperativoRequest, String token) {
		var informacionTokenDto = clientRest.validarToken(token).getRespuesta();
		log.info( "get reporte, usuario que consulta IdUsuario {} " , informacionTokenDto.getUsuario().getIdUsuario());
		return null;
	}

}

package com.pmsoluciones.innovacion.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pmsoluciones.innovacion.security.RefreshTokenDto;
import com.pmsoluciones.innovacion.security.RespuestaTokenDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClientRest {

	private final RestTemplate restTemplate;

	@Value("${api.webservices.msValidarToken}")
	private String msValidarToken;

	public RespuestaTokenDto validarToken(String token) {
		RefreshTokenDto refreshTokenDto = new RefreshTokenDto();
		refreshTokenDto.setRefreshToken(token);
		RespuestaTokenDto response = restTemplate.postForObject(msValidarToken, refreshTokenDto,RespuestaTokenDto.class);
		log.info("Respuesta validarToken: {}", response != null ? response.getEstatus() : null);
		return response;
	}

}

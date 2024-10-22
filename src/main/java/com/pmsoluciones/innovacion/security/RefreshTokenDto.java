package com.pmsoluciones.innovacion.security;

import java.io.Serializable;

import lombok.Data;

@Data
public class RefreshTokenDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String refreshToken;
	

}

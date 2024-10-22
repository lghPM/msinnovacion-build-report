package com.pmsoluciones.innovacion.security;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyAccessDeniedHandler implements AccessDeniedHandler {


    public static void writeCustomResponse(HttpServletResponse response, String mensaje, Integer statusCode) {
        if (!response.isCommitted()) {
            try {
            	String mensajeServer = mensaje == null ? "Acceso no autorizado.": mensaje;
            	Integer statusCodeServer = statusCode == null ? 403: statusCode;
            	JSONObject jsonResponse = new JSONObject();

            	jsonResponse.put("mensaje", mensajeServer);
            	jsonResponse.put("respuesta", "");
            	jsonResponse.put("estatus", false);
            	jsonResponse.put("code", statusCodeServer);

            	response.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            	response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write(jsonResponse.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		writeCustomResponse(response,null, null);
	}

}

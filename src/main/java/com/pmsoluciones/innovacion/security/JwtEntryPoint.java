package com.pmsoluciones.innovacion.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        MyAccessDeniedHandler.writeCustomResponse(response,  e.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
    }

}

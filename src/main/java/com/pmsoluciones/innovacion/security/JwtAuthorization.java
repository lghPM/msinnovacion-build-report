package com.pmsoluciones.innovacion.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class JwtAuthorization extends OncePerRequestFilter {
	
	@Value("${jwt.secret}")
    private String jwtSecret;

	private static final String HEADER = "Authorization";
	private static final String PREFIX = "Bearer ";
	private static UsuarioLdapDto usuario;

	public static UsuarioLdapDto getUsuario() {
		return usuario;
	}

	public static void setUsuario(UsuarioLdapDto usuario) {
		JwtAuthorization.usuario = usuario;
	}
	
	private Claims setSigningKey(HttpServletRequest request) {
		String jwtToken = request.
				getHeader(HEADER).
				replace(PREFIX, "");

		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey(jwtSecret))
				.build()
				.parseClaimsJws(jwtToken)
				.getBody();
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			if (isJWTValid(request, response)) {
				Claims claims = setSigningKey(request);
				if (claims.get("authorities") != null) {
					setAuthentication(claims);
				} else {
					SecurityContextHolder.clearContext();
				}
			} else {
				SecurityContextHolder.clearContext();
			}			
			chain.doFilter(request, response);			
		} catch (Exception e) {
			MyAccessDeniedHandler.writeCustomResponse(response,  "Token expirado o inválido.", HttpServletResponse.SC_FORBIDDEN);
		}
	}
	
	private boolean isJWTValid(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER);
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
			return false;
		return true;
	}
	
	
	private void setAuthentication(Claims claims) {
		List<String> authorities = (List<String>) claims.get("authorities");

		UsernamePasswordAuthenticationToken auth =
				new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
	private static Key getSigningKey(String secret) {
		byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * @param httpRequests
	 * @return
	 * @throws Exception
	 */
	public ResponseTokenDto validaToken(HttpServletRequest httpRequests) throws Exception {
		Gson gson = new Gson();
		RestTemplate restTemplate = new RestTemplate();
		String tokenHeader = httpRequests.getHeader(HEADER).replace(PREFIX, "");
		String resourceUrl = System.getenv().get("msValidarToken");

		JSONObject token = new JSONObject();
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
	    token.put("refreshToken", tokenHeader);

	    HttpEntity<String> request = new HttpEntity<>(token.toString(), headers);
	    String response   = restTemplate.postForObject(resourceUrl, request, String.class);
	    ResponseTokenDto responseToken = gson.fromJson(response, ResponseTokenDto.class);

	    if(responseToken == null || !responseToken.getEstatus()) {
	    	throw new Exception(responseToken.getMensaje());
	    }

		if (Boolean.TRUE.equals(responseToken.getEstatus())) {
			UsuarioLdapDto usuarioLogin = responseToken.getRespuesta().getUsuario();
			log.info("Usuario authenticado.:::: " + usuarioLogin.getNombreCompleto());
			usuarioLogin.setToken(tokenHeader);
			JwtAuthorization.setUsuario(usuarioLogin);
		}

	    return responseToken;
	}


}

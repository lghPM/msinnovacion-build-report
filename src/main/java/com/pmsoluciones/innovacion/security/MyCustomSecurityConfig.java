package com.pmsoluciones.innovacion.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class MyCustomSecurityConfig{

	@Autowired
	JwtAuthorization jwtAuthorizationFilter;
	
    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests( authz -> authz
                		.requestMatchers("/actuator/**").permitAll()
                		//.requestMatchers("/buscarProcesoDetalle").permitAll()
                        .requestMatchers("/v1/**")
						.hasAnyAuthority("ROLE_ADMINISTRADOR_CLIENTES", "ROLE_ADMINISTRADOR_GESTION_PROCESOS",
								"ROLE_COLABORADOR", "ROLE_DIRECCION_OPERACIONES", "ROLE_DIRECTIVO",
								"ROLE_LIDER_MEDICION", "ROLE_RESPONSABLE_NORMATIVIDAD_INNOVACION",
								"ROLE_RESPONSABLE_PROYECTO", "ROLE_RESPONSABLE_CONTRATO", "ROLE_SUPER_ADMINISTRADOR"
								,"ROLE_ADMIN_MEDICION_ANALISIS"
								)
                        .anyRequest().authenticated())
                .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(new MyAccessDeniedHandler());

        return http.build();
    }


}

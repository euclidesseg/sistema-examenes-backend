package com.sistema.examenes.sistemaexamenesbackend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;

import com.sistema.examenes.sistemaexamenesbackend.services.UserDetailService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
// * me premite especificar la configuracion para permitir el acceso a las
// distintas areas de la aplicacion como lo son servicios repositorios o
// controlador
@Configuration
@EnableMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
// * Habilita la seguridad a nivel metodo
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizeHandler;

	@Autowired
	private UserDetailService userDetailService;

	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public FilterChainProxy filterChainProxy(ServerHttpSecurity http) throws Exception {
		return (FilterChainProxy) http.build();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf( csrsConfig -> csrsConfig.disable()) // Adjust CSRF configuration as needed
				.cors( corsConfig -> corsConfig.disable()) // Adjust CORS configuration as needed
				.sessionManagement( sesionManagConfig -> sesionManagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProviser)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.authorizeHttpRequests(null)
				.anyExchange().authenticated()   
				.and() 
				.exceptionHandling().authenticationEntryPoint(unauthorizeHandler)
				.and()
				.pathMatchers(HttpMethod.OPTIONS).permitAll()
				.pathMatchers("/generate-token", "/usuarios/").permitAll()

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
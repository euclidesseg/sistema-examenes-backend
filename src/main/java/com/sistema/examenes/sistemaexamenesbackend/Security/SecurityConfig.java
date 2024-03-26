package com.sistema.examenes.sistemaexamenesbackend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;

import com.sistema.examenes.sistemaexamenesbackend.services.UserDetailServiceIMPL;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

// * me premite especificar la configuracion para permitir el acceso a las
// * distintas areas de la aplicacion como lo son servicios repositorios o
// * controlador
@EnableWebSecurity
@Component
@EnableMethodSecurity(prePostEnabled = true, jsr250Enabled = true)// * Habilita la seguridad a nivel metodo

public class SecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizeHandler;

	@Autowired
	private UserDetailServiceIMPL userDetailService;

	@Autowired(required = true)
	@Bean
	public AuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(null);
		provider.setPasswordEncoder(null);
		return provider;
	}
	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public FilterChainProxy filterChainProxy(ServerHttpSecurity http) throws Exception {
		return (FilterChainProxy) http.build();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrsConfig -> csrsConfig.disable()) // desactiva crsf
				.cors(corsConfig -> corsConfig.disable()) // desactiva cors
				.sessionManagement(	sesionManagConfig -> sesionManagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
				// .authorizeHttpRequests(builderRequstMathers());
		return http.build();
	}

	private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> builderRequstMathers() {
		return authConfig -> {
			authConfig.requestMatchers(HttpMethod.POST, "/generate-tocken").permitAll();
			authConfig.requestMatchers(HttpMethod.POST, "/usuarios/").permitAll();
			authConfig.requestMatchers(HttpMethod.OPTIONS).permitAll();
			authConfig.requestMatchers("/error").permitAll();

			authConfig.requestMatchers(HttpMethod.GET, "/auth/public-acess").permitAll();
			authConfig.anyRequest().authenticated();
		};
	}
}
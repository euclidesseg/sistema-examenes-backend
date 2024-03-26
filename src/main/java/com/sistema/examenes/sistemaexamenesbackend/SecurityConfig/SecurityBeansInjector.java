package com.sistema.examenes.sistemaexamenesbackend.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sistema.examenes.sistemaexamenesbackend.services.UserDetailServiceIMPL;

@Component
public class SecurityBeansInjector {
    
    @Autowired
    UserDetailServiceIMPL userDetailServiceIMPL;
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider()throws Exception{
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userDetailsService()); //== acceso a base de datos
        provider.setPasswordEncoder(passwordEncoder());//== encoder para comparar contraseñas una encriptada y otra no

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService( )throws Exception{
        return username ->{
            return this.userDetailServiceIMPL.loadUserByUsername(username);
        };
    }
}

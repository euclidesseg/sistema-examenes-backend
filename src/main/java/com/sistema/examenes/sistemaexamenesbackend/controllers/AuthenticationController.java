package com.sistema.examenes.sistemaexamenesbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.examenes.sistemaexamenesbackend.Jwt.JwtRequest;
import com.sistema.examenes.sistemaexamenesbackend.Jwt.JwtResponse;
import com.sistema.examenes.sistemaexamenesbackend.Security.JwtUtil;
import com.sistema.examenes.sistemaexamenesbackend.services.UserDetailServiceIMPL;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceIMPL userDetailServiceImpl;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/generate-tocken") //== este metodo llamara a la clase JwtRequest 
    public ResponseEntity<?> generateTocken (@RequestBody JwtRequest jwtRequest) throws Exception {
        
       try {
        autenticar(jwtRequest.getUsername(), jwtRequest.getPassword());

       } catch (Exception exception) {
        exception.printStackTrace();
        throw new Exception("No se ha podido generar el tocken");
       }

       UserDetails userDetails = this.userDetailServiceImpl.loadUserByUsername(jwtRequest.getUsername());
       String tocken = this.jwtUtil.generateToken(userDetails);
       return ResponseEntity.ok(new JwtResponse(tocken));
    }


    private void autenticar(String userame, String password) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userame, password));
        } catch (DisabledException disabledException) {
            throw new Exception("Usuario deshabilitado" + disabledException.getMessage());

        }catch(BadCredentialsException badCredentialsException){
            throw new Exception("Credenciales invalidas"+ badCredentialsException.getMessage());
        }
    }
    
}

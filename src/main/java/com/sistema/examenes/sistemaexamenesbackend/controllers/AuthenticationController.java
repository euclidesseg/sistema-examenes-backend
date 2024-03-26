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
import com.sistema.examenes.sistemaexamenesbackend.SecurityConfig.JwtUtil;
import com.sistema.examenes.sistemaexamenesbackend.services.UserDetailServiceIMPL;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login") //== este metodo llamara a la clase JwtRequest 
    public ResponseEntity<JwtResponse> generateTocken (@RequestBody JwtRequest jwtRequest) throws Exception {
        return null;
    }

    
}

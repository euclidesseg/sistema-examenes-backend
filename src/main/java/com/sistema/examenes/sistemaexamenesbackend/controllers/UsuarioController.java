package com.sistema.examenes.sistemaexamenesbackend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.examenes.sistemaexamenesbackend.entities.Rol;
import com.sistema.examenes.sistemaexamenesbackend.entities.Usuario;
import com.sistema.examenes.sistemaexamenesbackend.entities.UsuarioRol;
import com.sistema.examenes.sistemaexamenesbackend.services.UsuarioService;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/")
    public Usuario guardarUsuario(@RequestBody Usuario usuarioGuardar)throws Exception {
        Set<UsuarioRol> roles = new HashSet<UsuarioRol>();

        Rol rol = new Rol();
        rol.setId(2L);
        rol.setName("NORMAL");

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setRol(rol);
        usuarioRol.setUsuario(usuarioGuardar);

        roles.add(usuarioRol);

        return this.usuarioService.guardarUsuario(usuarioGuardar, roles);
    }

    @GetMapping(path = "/{username}")
    public Usuario getUsuario(@PathVariable("username") String username){
        return this.usuarioService.obtenerUsuario(username);
    }
    
    @DeleteMapping(path = "/{usuarioId}")
    public boolean deleteUsuario(@PathVariable("usuarioId") Long usuarioId) throws Exception{
        return this.usuarioService.eliminarUsuario(usuarioId);
    }
}

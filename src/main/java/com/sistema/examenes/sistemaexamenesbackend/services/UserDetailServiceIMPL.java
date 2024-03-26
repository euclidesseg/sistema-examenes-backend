package com.sistema.examenes.sistemaexamenesbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sistema.examenes.sistemaexamenesbackend.entities.Usuario;
import com.sistema.examenes.sistemaexamenesbackend.repositories.IUsuarioRepository;

@Service
public class UserDetailServiceIMPL implements UserDetailsService { // implementa de userDetailService clase de Spring
                                                               // securyti para buscar un usuario por username

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Usuario usuario = this.usuarioRepository.findByUsername(username); //!obtengo el usuario
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }else{
            return usuario;
        }
    }

}

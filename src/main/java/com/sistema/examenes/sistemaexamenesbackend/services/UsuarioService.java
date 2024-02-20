package com.sistema.examenes.sistemaexamenesbackend.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.examenes.sistemaexamenesbackend.entities.Usuario;
import com.sistema.examenes.sistemaexamenesbackend.entities.UsuarioRol;
import com.sistema.examenes.sistemaexamenesbackend.interfaces.IUsuarioService;
import com.sistema.examenes.sistemaexamenesbackend.repositories.IRolRepository;
import com.sistema.examenes.sistemaexamenesbackend.repositories.IUsuarioRepository;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    IUsuarioRepository usuarioRepository;
    @Autowired
    IRolRepository rolRepository;

    
    @SuppressWarnings("null")
    @Override // sobreescribo el metodo la interfaz de usuario para guardar un usuario
    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception {
        Usuario usuarioAGuardar = usuarioRepository.findByUsername(usuario.getUsername());
        
        if(usuarioAGuardar != null){
            System.out.println("El usuario ya existe");
            throw new Exception("EL usuario ya existe en la base de datos");
        }else{
            for(UsuarioRol usuarioRol:usuarioRoles){ // itera sobre el set de datos de usuarioroles este set contiene objetos de tipo Rol y de tipo Usuario que indican la relacion entre un usuaro y un rol
                rolRepository.save(usuarioRol.getRol()); //guarda el rol del usuario en la tabla rol este rol contiene un objeto de tipo ROl que es el id del rol y el nombre
            }
            usuario.getUsuarioRol().addAll(usuarioRoles); //usuario.getUsuarioRol() retorna una coleccion vacia, por lo tanto addAll agrega los elementos de la coleccion usuarioRoles a la coleccion que me obtiene el metodo getUsuarioRol 
            usuarioAGuardar = usuarioRepository.save(usuario);
        }
        return usuarioAGuardar;
    }

}

//Nota un set es un objeto con objetos de tipo usuario y rol
// debido a que en la clase usuario estamos usando cascade y tiene relacion con la tabla UsuarioRol jpa buscara la tabla UsuaioRol y mapeara el set de tipo UsuarioRol
// a esa tabla


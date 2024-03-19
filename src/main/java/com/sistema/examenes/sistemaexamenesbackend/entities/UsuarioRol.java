package com.sistema.examenes.sistemaexamenesbackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class UsuarioRol {
    
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioRolId;

    @ManyToOne(fetch = FetchType.EAGER)  // @ManyToOne significa que muchos objetos de la clase UsuarioRol pueden estar asociados
                                         // con un solo objeto de la clase Usuario y un solo objeto de la clase Rol
    private Usuario usuario;

    

    @ManyToOne              // @ManyToOne significa que muchos objetos de la clase UsuarioRol pueden estar asociados
                           // con un solo objeto de la clase Usuario y un solo objeto de la clase Rol
    private Rol rol;


    //metodos
    public Long getUsuarioRolId() {
        return usuarioRolId;
    }

    public void setUsuarioRolId(Long usuarioRolId) {
        this.usuarioRolId = usuarioRolId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    } 
   
    
}
// Eplicacion de 
// @ManyToOne(fetch = FetchType.EAGER) 
// private Usuario usuario;

// @ManyToOne
// private Rol rol;

/*
 * Un usuario puede tener rol de usuario por lo tanto en la tabla usuarioRol debera tener un objeto que lo relacione con ese rol
 * el mismo usuario puede tener permisos tambien de super usuario por lo tanto debe tener un obeto que lo relacione con ese rol 
 * y por eso la relacion indica que muchos objetos de usuarioROl estaran relacionados con un Usuario y/o un Rol
 */

  
// {usuarioRolId:1, usuario:1, rol:admin }
// {usuarioRolId:3, usuario:2, rol:normal }
// {usuarioRolId:2, usuario:1, rol:normal }   

// Estos objetos muestrean la relacion mmany to one donde un de esta clase le pertenece a la vez 
// a varios objetos con el mismo rol y a varios objetos con el mismo usuario

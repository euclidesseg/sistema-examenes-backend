package com.sistema.examenes.sistemaexamenesbackend.interfaces;

import java.util.Set;
import com.sistema.examenes.sistemaexamenesbackend.entities.Usuario;
import com.sistema.examenes.sistemaexamenesbackend.entities.UsuarioRol;

public interface IUsuarioService {
    

    // metodo para guardar un usuario debo mandarle un usuair y un set de Usuaio rol que indica los roles que tendra este usuario
    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol>usuarioRoles) throws Exception;

     public Usuario obtenerUsuario (String username);

     public void eliminarUsuario(Long usuaioId) throws Exception;
}

package com.sistema.examenes.sistemaexamenesbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.examenes.sistemaexamenesbackend.entities.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long>{
    
    public Usuario findByUsername (String username);
}

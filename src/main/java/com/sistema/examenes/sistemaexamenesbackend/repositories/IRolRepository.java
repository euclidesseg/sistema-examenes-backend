package com.sistema.examenes.sistemaexamenesbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.examenes.sistemaexamenesbackend.entities.Rol;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Long>{
    
}

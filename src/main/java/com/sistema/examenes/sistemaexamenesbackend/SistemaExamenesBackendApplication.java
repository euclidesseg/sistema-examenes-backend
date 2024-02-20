package com.sistema.examenes.sistemaexamenesbackend;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sistema.examenes.sistemaexamenesbackend.entities.Rol;
import com.sistema.examenes.sistemaexamenesbackend.entities.Usuario;
import com.sistema.examenes.sistemaexamenesbackend.entities.UsuarioRol;
import com.sistema.examenes.sistemaexamenesbackend.interfaces.IUsuarioService;

@SpringBootApplication
public class SistemaExamenesBackendApplication implements CommandLineRunner {

	@Autowired
	private IUsuarioService usuarioService; // esta injecion buscara a su interfaz, es decir, interfaz que implementa y llamará a un metodo

	public static void main(String[] args) {
		SpringApplication.run(SistemaExamenesBackendApplication.class, args);
	}

	// implementamos de CommandLineRunner para que el seiguiente metodo se ejecute
	@Override
	public void run(String... args) throws Exception {
		// Creación de un nuevo usuario
		Usuario usuario = new Usuario();
		usuario.setNombre("Euclides");
		usuario.setApellido("Pérez");
		usuario.setUsername("euclides");
		usuario.setPassword("123456");
		usuario.setEmail("euclides@gmail.com.co");
		usuario.setTelefono("3122349449");
		usuario.setPerfil("foto.png");
		

		// Creación de un nuevo rol
		Rol rol = new Rol();
		rol.setId(1L);
		rol.setName("ADMIN");

		// Creación de una relación entre el usuario y el rol
		//usuarioRoles es un conjunto (Set) que contiene múltiples objetos de tipo UsuarioRol. 
		Set<UsuarioRol> usuarioRoles = new HashSet<UsuarioRol>();

		// creacion de un objeto de tipo HashSet<UsuarioRol>, contendra objetos de ripo Usuario y de tipo Rol
		//usuarioRol, por otro lado, es un solo objeto de tipo UsuarioRol un objeto de tipo Rol y uno de tipo ROl
		UsuarioRol usuarioRol = new UsuarioRol();
		usuarioRol.setRol(rol);
		usuarioRol.setUsuario(usuario);

		// agregamos al HashSet los objetos usuairoRol, aqui ya contiene los objetos necesarios
		usuarioRoles.add(usuarioRol);

		// Guardado del usuario con su rol asociado utilizando el servicio de usuario
		Usuario usuarioGuardado =  usuarioService.guardarUsuario(usuario, usuarioRoles);
		System.out.println(usuarioGuardado.getUsername());
	}

}


// Nota: estructura Json que representa el HashSet de usuarioRoles
/*
 * {
  "usuarioRoles": [				=> set de tipo UsuarioRol
    { 							=> objeto de tipo UsuarioRol
      "usuario": {      		=> onjeto de tipo usuario
        "nombre": "Euclides",
        "apellido": "Pérez",
        "username": "euclides",
        "email": "euclides@gmail.com.co",
        "telefono": "3122349449",
        "perfil": "foto.png"
      },
      "rol": {					=> objeto de tipo Rol
        "id": 1,
        "name": "ADMIN"
      }
    }
  ]
}
 */
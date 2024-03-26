package com.sistema.examenes.sistemaexamenesbackend.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// autoincrementable
    @Column(unique = true, nullable = false )
    private Long id;

    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private String email; 
    private String telefono;
    private boolean enabled = true;
    private String perfil; 

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario") // relacion con usuarioRol lo que quiere decir
                                                                                         //que un usuairo peude tener muchos roles
    @JsonIgnore
    private Set<UsuarioRol> usuarioRoles = new HashSet<>();



    //metodos
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Set<UsuarioRol> getUsuarioRol() {
        return usuarioRoles;
    }

    public void setUsuarioRol(Set<UsuarioRol> usuarioRol) {
        this.usuarioRoles = usuarioRol;
    }


    public Usuario(){
        
    }


    //... Desde aqui implementamos seguriadad

    //... Me va a retornar una coleccion de autoridades es decir los permisos o roles del usuario
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //== permisos concedidos
        Set<Authority>  authorities = new HashSet<>();

        this.usuarioRoles.forEach((usuarioRol) ->{ // recorremos y retornamos los roles de un usuario para saber que permisos tiene estos son agregados a un HasSet 
            authorities.add(new Authority("ROLE_"+ usuarioRol.getRol().getName()));
        } );
        return authorities;
        //![{"authority":"ROLE_ADMIN"}, {"authority":"ROLE_USER"}]

    }

    @Override  // la cuenta esta expirada?
    public boolean isAccountNonExpired() {
      return true;
    }

    @Override  // la cuenta no está bloqueada
    public boolean isAccountNonLocked() {
       return true;
    }

    @Override // las cuentas no estan expiradas expirar?
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
}

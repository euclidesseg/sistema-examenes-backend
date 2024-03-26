package com.sistema.examenes.sistemaexamenesbackend.SecurityConfig;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sistema.examenes.sistemaexamenesbackend.services.UserDetailServiceIMPL;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// # deste esta clase llamare a los metodos de la clase JwtUtil para implementar la lógica de extraer el tocken y validarlo
// # Esta clase se centra principalmente en la validación y procesamiento de un token JWT recibido en una solicitud entrante para autenticar al usuario.
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailServiceIMPL userDetailServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String requestTokendHeader = request.getHeader("Authorization"); // == Obtener el token de autorización de la cabecera de la solicitud
        String username = null; // == Inicializar variables para almacenar el nombre de usuario y el token JWT
        String jwtToken = null;
        //!---------------------------------------------------------------------------------------------------------------------------------------------------------------
        if (requestTokendHeader != null && requestTokendHeader.startsWith("Bearer ")) { // == Verificar si el token de autorización existe y comienza con "Bearer "
            jwtToken = requestTokendHeader.substring(7); // == Extraer el token JWT eliminando el prefijo "Bearer "
            try {
                username = this.jwtUtil.extractUsername(jwtToken); // == Extraer el nombre de usuario del token JWT utilizando la clase JwtUtil
            } catch (ExpiredJwtException expiredJwtException) {
                System.out.println("El token ha expirado"); // == Manejar la excepción si el token ha expirado
            } catch (Exception exception) {
                exception.printStackTrace(); // == Manejar cualquier otra excepción que pueda ocurrir durante la extracción del nombre de usuario
            }
        } else {
            System.out.println("No empieza por Bearer string"); // == Imprimir un mensaje si el token de autorización no comienza con "Bearer "
        }

        //? Este bloque de código anterior se encarga de extraer el token de autorización de la cabecera de la solicitud HTTP y, si el token comienza con "Bearer",
        //?  extrae el token JWT eliminando el prefijo "Bearer"
        //!---------------------------------------------------------------------------------------------------------------------------------------------------------------

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { // == Verificar si se ha extraído con éxito el nombre de usuario del token JWT y verificar si en el contexto de la seguridad hay una autenticación establecida
            UserDetails userDetails = this.userDetailServiceImpl.loadUserByUsername(username); // == Cargar los detalles del usuario utilizando el servicio de detalles de usuario con el username extraido antes clase de spring securyti core
            if (this.jwtUtil.validateToken(jwtToken, userDetails)) { // == Validar el token JWT para verificar si es válido y está asociado con el usuario
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;// == crea un objeto para crear almacenar la autenticación de un usuario en el contexto de spring security
                usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities()); //== se establecen los detalles del usuario que se esta autenticando
                                                                                                                                                                                               
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));//== Establece los detalles adicionales de la autenticación del usuario. En este caso, se está agregando información sobre la solicitud HTTP que se está autenticando.
                                                                                                                           //== estos detalles no son los mismos que el detalle de usuario los detalles de la peticion son  por ejemplo, la dirección IP del cliente, el navegador web utilizado, la hora de la solicitud, etc. 
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken); // == Establecer la autenticación en el contexto de seguridad
            } else {
                System.out.println("El token no es válido"); // == Imprimir un mensaje si el token JWT no es válido
            }
            filterChain.doFilter(request, response); // == Permitir que la solicitud continúe su procesamiento normal
        }
    }
    //!Nota: 
    //== Cuuando realizas una llamada como userDetails.getAuthorities(), Spring Security invocará este método en la implementación de UserDetails, 
    //== que en mi caso es la clase Usuario, para obtener las autoridades del usuario.
    
}

// # La clase JwtAuthenticationFilter es un filtro de Spring Security
// # diseñado para interceptar las solicitudes entrantes y realizar la
// # autenticación del usuario utilizando tokens JWT.


//... SecurityContextHolder.getContext().getAuthentication() == null)
//?  si todos los datos de la peticion del usuario vienen en el request proque
//?  este metodo no hace uso de ella para saber entonces  la informacion de la autenticacion
//# El método SecurityContextHolder.getContext().getAuthentication() no necesita acceder
//# directamente a la solicitud (HttpServletRequest) para obtener la información
//# de autenticación. En cambio, obtiene la información de autenticación directamente del contexto de seguridad de la aplicación.
package com.sistema.examenes.sistemaexamenesbackend.Security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//# Esta clase se encargara de tomar el header el pyload crear la signature y validar el tocken
@Component
public class JwtUtil {
    
    private String SECRET_KEY = "QGBQD((E))@";

    //== Extrae el nomnre del usuario del tocken
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //== Extrae la fecha de expiracion del tocken
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //== Utilidad para extraer cualquier reclamo del tocken (clain)
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { //== El tipo genérico <T> se utiliza para permitir la extracción de cualquier tipo de reclamo del token.
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /*// # La sintaxis Function<Claims, T> indica que extractClaim toma una función que toma un objeto Claims como entrada y devuelve un tipo T */
    /*//# Claims es una clase proporcionada por la biblioteca io.jsonwebtoken  */
    /*//# los claims o reclamos Estos reclamos se almacenan en la sección de payload del token JWT  y pueden ser utilizados para transmitir información como el identificador del usuario, roles, */



    //== Extrae todos los reclamos del del tocken
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    //== verifica  si el tocken ah expirado retorna true o false
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //== Genera un tocken JWT basado en los detalles del usuario
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    //== Crea un tocken JWT con los reclamos dados y el sujeto proporcionado
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder() //# Inicio de la construcciond el tocke
                .setClaims(claims)  //# Configuración de los reclamos (claims) del token o informacion del sujeto
                .setSubject(subject) //# establecer el sujeto del tocken generalmente el nombre del usuario
                .setIssuedAt(new Date(System.currentTimeMillis())) //# estableces la fecha de emision del tocken
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  //# estableces la fecha de expiracion del tocke
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) //# firmar el tocken con el algoritmo y la clave secreta 
                .compact(); // # compactar el tocken en una cadena 
    }

    //== verifica si el token es valido par el usuario dado retorna true o false  necesita un tocken con datos de usuario y los datos del usuario para comparar que sean los mismos
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}


//... no necesitas preocuparte por la conversión de los datos a base64 o la firma del token manualmente en tu código.
//... La biblioteca Jwts se encarga de hacerlo de manera transparente cuando utilizas su API para construir y firmar el token.
package com.sistema.examenes.sistemaexamenesbackend.Jwt;

// esta clase me servira para responder la peticion de un tocken
public class JwtResponse {
    private String tocken;


    public JwtResponse(){

    }
    public JwtResponse(String tocken){
        this.tocken = tocken;
    }

    
    //! Getters y Setters
    public String getTocken() {
        return tocken;
    }
    public void setTocken(String tocken) {
        this.tocken = tocken;
    }
}

package com.sistema.examenes.sistemaexamenesbackend.Jwt;


// Esta clase me va a ayudar a mandar la peticion para solicitar un tocken
public class JwtRequest{
    

    private String username;
    private String password;


    public JwtRequest(){
        
    }

    public JwtRequest(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getUsername(){
        return this.username;
    }
    public void setUsername(String userame){
        this.username = userame;
    }
}

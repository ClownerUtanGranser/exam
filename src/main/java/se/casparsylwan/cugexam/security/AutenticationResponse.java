package se.casparsylwan.cugexam.security;




public class AutenticationResponse {

    private final String jwt;

    public AutenticationResponse(String jwt){
        this.jwt = jwt;
    }

    public String getJwt(){
        return jwt;
    }
}

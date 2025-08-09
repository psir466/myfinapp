package org.psi.myfinappbackapp.dto;

public class JwtAuthenticationResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private String username;

    public JwtAuthenticationResponse(String accessToken, String username) {
        this.accessToken = accessToken;
        this.username = username;
    }

    // Getters et setters

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

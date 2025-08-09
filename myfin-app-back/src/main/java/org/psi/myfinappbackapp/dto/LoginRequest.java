package org.psi.myfinappbackapp.dto;

public class LoginRequest {


     private String usernameOrEmail;
    private String password;

    // Constructeurs, getters et setters

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

package com.example.back.dto;

// import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequestDTO {
    // ce n'est pas le champs login qui est utilisé côté front
    // @JsonProperty("login")
    // front
    private String email;
    private String password;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequestDTO{email='" + email + "', passwordLength=" + (password != null ? password.length() : 0)
                + '}';
    }
}

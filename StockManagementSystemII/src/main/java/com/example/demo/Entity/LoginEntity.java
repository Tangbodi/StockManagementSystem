package com.example.demo.Entity;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
public class LoginEntity {
    public LoginEntity(){

    }
    @NotEmpty
    private String email ;

    @NotEmpty
    private String password;



    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "{" +
                ", email='" + getEmail() + "'" +
                ", password='" + getPassword() + "'" +
                "}";
    }
}

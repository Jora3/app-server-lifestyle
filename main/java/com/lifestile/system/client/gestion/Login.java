package com.lifestile.system.client.gestion;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login implements Serializable {
    private String email;
    private String password;

    public Login(String email, String password) throws Exception {
        this.setEmail(email);
        this.setPassword(password);
    }

    public Login() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches())
            this.email = email;
        else
            throw new Exception("Email invalide : " + email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

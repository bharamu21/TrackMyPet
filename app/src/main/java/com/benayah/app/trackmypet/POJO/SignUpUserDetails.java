package com.benayah.app.trackmypet.POJO;

/**
 * Created by Benayah on 30/6/2017.
 */

public class SignUpUserDetails {

    private String userName,email,password;

    public SignUpUserDetails(String userName,String email,String password)
    {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}

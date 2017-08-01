package com.benayah.app.trackmypet.POJO;

/**
 * Created by User on 06-07-2017.
 */

public class SignInDetails {

    private String signInEmail;
    private String signInPassword;

    public SignInDetails(String signInEmail,String signInPassword)
    {
        this.signInEmail = signInEmail;
        this.signInPassword = signInPassword;
    }

    public void setSignInEmail(String signInEmail) {
        this.signInEmail = signInEmail;
    }

    public String getSignInEmail() {
        return signInEmail;
    }

    public void setSignInPassword(String signInPassword) {
        this.signInPassword = signInPassword;
    }

    public String getSignInPassword() {
        return signInPassword;
    }
}

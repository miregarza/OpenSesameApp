package com.example.opensesameapp;

/**
 * Created by User on 3/14/2017.
 */

public class Platform {
    private String platform;
    private String password;

    public Platform(String password, String platform) {
        this.platform = platform;
        this.password = password;
    }

    public String getplatform() {
        return platform;
    }

    public void setplatform(String platform) {
        this.platform = platform;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }
}


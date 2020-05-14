package com.example.healthylives.Social;

/**
 * Users for the firebase users node
 */
public class Users {
    private String username;

    public Users(String u)
    {
        username = u;
    }

    public Users()
    {
        username = new String();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

package com.example.progettoappnotes;

public class User {
    private String username;
    private String email;
    private String password;
    private String login; ///può essere sia email che password

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }


    // (Getter e Setter opzionali)
}

package com.example.progettoappnotes;

public class User {
    private String username;
    private String email;
    private String password;

    // Costruttore per login con email
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Costruttore per login con username
    public User(boolean isUsername, String usernameOrEmail, String password) {
        if (isUsername) {
            this.username = usernameOrEmail;
        } else {
            this.email = usernameOrEmail;
        }
        this.password = password;
    }

    // Getters
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}

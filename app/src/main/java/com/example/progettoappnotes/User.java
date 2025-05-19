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

    // (Getter e Setter opzionali)
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
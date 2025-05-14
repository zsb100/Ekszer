package com.example.ekszer;

public class User {

    private String name;
    private String email;
    private String username;

    private String id;

    public User(String name, String email, String username) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.id = "";
    }

    public User() {
        this.name = "";
        this.email = "";
        this.username = "";
        this.id = "";
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String _getId() {
        return id;
    }

    public void _setId(String id) {
        this.id = id;
    }
}

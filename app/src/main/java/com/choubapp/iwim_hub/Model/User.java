package com.choubapp.iwim_hub.Model;

public class User {
    private String nom, prenom, email, password, type;

    public User(String nom, String prenom, String email, String password, String type) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.type= type;
    }

    public User() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

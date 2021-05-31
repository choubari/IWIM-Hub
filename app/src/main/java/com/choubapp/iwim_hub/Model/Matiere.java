package com.choubapp.iwim_hub.Model;

public class Matiere {
    private String matiere, module, responsable, horaire;


    public Matiere() {
    }

    public Matiere(String matiere, String module, String responsable, String horaire) {
        this.matiere = matiere;
        this.module = module;
        this.responsable = responsable;
        this.horaire = horaire;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }
}

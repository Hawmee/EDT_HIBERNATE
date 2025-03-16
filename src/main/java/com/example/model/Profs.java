package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "profs")
public class Profs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codeprof;
    private String nom;
    private String prenoms;
    private String grade ;

    public Profs(){}
    public Profs(String nom , String prenoms , String grade){
        this.nom = nom;
        this.prenoms= prenoms;
        this.grade= grade;
    }

    public int getCodeprof(){
        return codeprof;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString(){
        return nom+" "+prenoms;
    }

}

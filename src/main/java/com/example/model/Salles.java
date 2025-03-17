package com.example.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "salles")
public class Salles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codesal;
    private String designation;

    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Occupations> occupations;

    public Salles(){}
    public Salles(String designation ){
        this.designation = designation;
    }


    public int getCodesal() {
        return codesal;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString(){
        return designation;
    }
}

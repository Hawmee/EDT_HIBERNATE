package com.example.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "occuper")
public class Occupations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "codeprof" , nullable = false)
    private Profs prof;

    @ManyToOne
    @JoinColumn(name = "codesal" , nullable = false)
    private Salles salle;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    public Occupations(){}
    public Occupations(Profs prof , Salles salle , LocalDateTime date){
        this.prof = prof;
        this.salle= salle;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Profs getProf() {
        return prof;
    }

    public void setProf(Profs prof) {
        this.prof = prof;
    }

    public Salles getSalle() {
        return salle;
    }

    public void setSalle(Salles salle) {
        this.salle = salle;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return prof+"-"+salle+"-"+"("+date+")";
    }

}

package com.example.model;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

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
    private Date date;

    public Occupations(){}
    public Occupations(Profs prof , Salles salle , Date date){
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFormatedDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM yyyy , HH:mm" , Locale.FRENCH);
        return sdf.format(date);
    }

    @Override
    public String toString(){
        return prof+"-"+salle+"-"+"("+date+")";
    }

}

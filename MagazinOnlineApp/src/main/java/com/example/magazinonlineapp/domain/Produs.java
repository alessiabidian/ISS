package com.example.magazinonlineapp.domain;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "produse")
public class Produs{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "denumire")
    private String denumire;

    @Column(name = "pret")
    private int pret;

    @Column(name = "stoc")
    private int stoc;

    public Produs() {}

    public Produs(int id, String denumire, int pret, int stoc) {
        this.id = id;
        this.denumire = denumire;
        this.pret = pret;
        this.stoc = stoc;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public int getStoc() {
        return stoc;
    }

    public void setStoc(int stoc) {
        this.stoc = stoc;
    }
}

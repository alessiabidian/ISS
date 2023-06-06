package com.example.magazinonlineapp.domain;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "clienti")
public class Client{
    @Column(name = "nume")
    private String nume;

    @Column(name = "prenume")
    private String prenume;

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "parola")
    private String parola;

    public Client() {}

    public Client(String nume, String prenume, String email, String parola) {
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.parola = parola;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}

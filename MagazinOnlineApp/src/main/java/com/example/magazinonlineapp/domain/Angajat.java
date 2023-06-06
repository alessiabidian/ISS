package com.example.magazinonlineapp.domain;
import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "angajati")
public class Angajat{
    @Column(name = "nume")
    private String nume;

    @Column(name = "prenume")
    private String prenume;

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "parola")
    private String parola;

    public Angajat(String nume, String prenume, String username, String parola) {
        this.nume = nume;
        this.prenume = prenume;
        this.username = username;
        this.parola = parola;
    }

    public Angajat() {}

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}

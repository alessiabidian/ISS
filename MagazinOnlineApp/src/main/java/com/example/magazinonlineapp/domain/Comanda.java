package com.example.magazinonlineapp.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Table(name = "comenzi")
public class Comanda{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data")
    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    @Column(name = "stadiucurent")
    private Stadiu stadiuCurent;

    @Column(name = "emailclient")
    private String emailclient;

    public Comanda(int id, LocalDateTime data, Stadiu stadiuCurent, String emailclient) {
        this.id = id;
        this.data = data;
        this.stadiuCurent = stadiuCurent;
        this.emailclient = emailclient;
    }

    public String getEmailclient() {
        return emailclient;
    }

    public void setEmailclient(String emailclient) {
        this.emailclient = emailclient;
    }

    public Comanda() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Stadiu getStadiuCurent() {
        return stadiuCurent;
    }

    public void setStadiuCurent(Stadiu stadiuCurent) {
        this.stadiuCurent = stadiuCurent;
    }
}

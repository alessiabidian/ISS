package com.example.magazinonlineapp.domain;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "produsecomenzi")
public class ProdusComanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "idcomanda")
    private int idComanda;

    @Column(name = "idprodus")
    private int idProdus;

    @Column(name = "cantitate")
    private int cantitate;


    public ProdusComanda() {
    }

    public ProdusComanda(int id, int idComanda, int idProdus, int cantitate) {
        this.id = id;
        this.idComanda = idComanda;
        this.idProdus = idProdus;
        this.cantitate = cantitate;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public int getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }
}

package com.example.magazinonlineapp.domain;

public class Entity<ID> {
    private ID id;

    public ID getID(){
        return id;
    }

    public void setID(ID id){ this.id = id; }
}

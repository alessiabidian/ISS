package com.example.magazinonlineapp.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basket {
    private List<Produs> produse;

    public Basket() {
        this.produse = new ArrayList<>();
    }

    public Basket(List<Produs> produse) {
        this.produse = produse;
    }

    public void addProdus(Produs produs){
        this.produse.add(produs);
    }

    public List<Produs> getProduse() {
        return produse;
    }

    public void setProduse(List<Produs> produse) {
        this.produse = produse;
    }

    public void clearBasket() {
        this.produse.clear();
    }

//    private Map<Produs, Integer> productFrequencies;
//
//    public Basket() {
//        productFrequencies = new HashMap<>();
//    }
//
//    public void addProdus(Produs product) {
//        Integer frequency = productFrequencies.getOrDefault(product, 0);
//        productFrequencies.put(product, frequency + 1);
//    }
//
//    public void removeProdus(Produs product) {
//        Integer frequency = productFrequencies.getOrDefault(product, 0);
//        if (frequency > 1) {
//            productFrequencies.put(product, frequency - 1);
//        } else {
//            productFrequencies.remove(product);
//        }
//    }
//
//    public Map<Produs, Integer> getProduse(){
//        return productFrequencies;
//    }
}

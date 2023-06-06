package com.example.magazinonlineapp.service;

import com.example.magazinonlineapp.domain.*;
import com.example.magazinonlineapp.domain.validators.ClientValidator;
import com.example.magazinonlineapp.domain.validators.ValidationException;
import com.example.magazinonlineapp.domain.validators.Validator;
import com.example.magazinonlineapp.observer.ObservableImpl;
import com.example.magazinonlineapp.repository.db.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Service extends ObservableImpl {
    AngajatDbRepository repoAngajat;

    ClientDbRepository repoClient;

    ProdusDbRepository repoProdus;
    ComandaDbRepository repoComanda;
    ProdusComandaDbRepository repoPC;
    Validator<Client> clientValidator;

    public Service(AngajatDbRepository repoAngajat, ClientDbRepository repoClient, ProdusDbRepository repoProdus, ComandaDbRepository repoComanda, ProdusComandaDbRepository repoPC) {
        this.repoAngajat = repoAngajat;
        this.repoClient = repoClient;
        this.repoProdus = repoProdus;
        this.repoComanda = repoComanda;
        this.repoPC = repoPC;
        this.clientValidator = new ClientValidator();
    }

    public List<Produs> getAllProduse(){
        return repoProdus.findAll();
    }

    public List<Produs> filterProduse(String denumire, int pret){
        return repoProdus.findByDenumirePret(denumire, pret);
    }

    public boolean loginClient(String email, String parola){
        Client client = repoClient.findByEmail(email);

        if(client != null)
        {
            return Objects.equals(client.getParola(), parola);
        }

        return false;
    }

    public boolean loginAngajat(String username, String parola){
        Angajat angajat = repoAngajat.findByUsername(username);

        if(angajat != null)
        {
            return Objects.equals(angajat.getParola(), parola);
        }

        return false;
    }

    public void addClient(String nume, String prenume, String email, String parola) throws ValidationException {
        Client client = new Client(nume, prenume, email, parola);
        clientValidator.validate(new Client(nume, prenume, email, parola));

        if(findClient(email)!=null)
            throw new ValidationException("Email already used!");

        repoClient.save(client);
    }

    public Client findClient(String email){
        return repoClient.findByEmail(email);
    }

    public Comanda updateComanda(Comanda comanda){
        Comanda c = repoComanda.update(comanda);
        notifyObservers();
        return c;
    }

    public int getCantitate(Produs object, List<Produs> lista) {
        int count = 0;
        for (Object o : lista) {
            if (Objects.equals(object, o))
                count++;
        }
        return count;
    }

    public void addComanda(List<Produs> produse, String idClient) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime data = LocalDateTime.now();
        Comanda comanda = new Comanda(0, data, Stadiu.Pending, idClient);
        repoComanda.save(comanda);

        List<Produs> basket = produse.stream().distinct().toList();

        for(Produs produs : basket)
        {
            int cantitate = getCantitate(produs, produse);
            ProdusComanda produsComanda = new ProdusComanda(0, comanda.getId(), produs.getID(), Math.min(produs.getStoc(), cantitate));
            repoPC.save(produsComanda);

            produs.setStoc(Math.max(produs.getStoc() - cantitate, 0));

            repoProdus.update(produs);
        }
        notifyObservers();
    }

    public List<Comanda> getComenziClient(String email) {
        return repoComanda.findByClient(email);
    }

    public List<ProdusComanda> getDetaliiComanda(int idComanda) {
        return repoPC.findByIDComanda(idComanda);
    }

    public Produs findProdus(int idProdus) {
        return repoProdus.findByID(idProdus);
    }

    public List<Comanda> filterComenzi(Stadiu stadiu) {
        return repoComanda.findByStadiu(stadiu);
    }

    public Angajat findAngajat(String username) {
        return repoAngajat.findByUsername(username);
    }

    public void deleteComanda(Comanda c) {
        List<ProdusComanda> produseComanda = getDetaliiComanda(c.getId());
        int cantitate = 0;
        int idProdus = 0;

        for(ProdusComanda pc : produseComanda) {
            cantitate = pc.getCantitate();
            idProdus = pc.getIdProdus();

            //stergem detaliul din comanda cu produsu si cantitatea
            repoPC.delete(pc);

            //reinnoim stocul - creste inapoi cum era inainte de comanda
            Produs produs = repoProdus.findByID(idProdus);
            produs.setStoc(produs.getStoc() + cantitate);
            repoProdus.update(produs);
        }

        //putem sterge acum comanda
        repoComanda.delete(c);
        notifyObservers();
    }

    public void updateProdus(Produs p) {
        repoProdus.update(p);
        notifyObservers();
    }

    public void deleteProdus(Produs p) throws Exception {
        if(repoPC.findByIDProdus(p.getID()).size()>0)
            throw new Exception("Cannot delete product");
        repoProdus.delete(p);
        notifyObservers();
    }
}

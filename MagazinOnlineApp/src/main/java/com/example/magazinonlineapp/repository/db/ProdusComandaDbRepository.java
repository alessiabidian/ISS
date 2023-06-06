package com.example.magazinonlineapp.repository.db;

import com.example.magazinonlineapp.domain.Produs;
import com.example.magazinonlineapp.domain.ProdusComanda;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class ProdusComandaDbRepository {

    static SessionFactory sessionFactory;

    public ProdusComandaDbRepository() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Exceptie " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }

    }

    public List<ProdusComanda> findByIDComanda(Integer idComanda) {
        Session session = sessionFactory.openSession();
        List<ProdusComanda> produsecomenzi = session.createQuery("FROM ProdusComanda as p WHERE p.idComanda = :idcomanda", ProdusComanda.class)
                .setParameter("idcomanda", idComanda)
                .list();
        session.close();
        return produsecomenzi;
    }

    public List<ProdusComanda> findByIDProdus(Integer idProdus) {
        Session session = sessionFactory.openSession();
        List<ProdusComanda> produsecomenzi = session.createQuery("FROM ProdusComanda as p WHERE p.idProdus = :idprodus", ProdusComanda.class)
                .setParameter("idprodus", idProdus)
                .list();
        session.close();
        return produsecomenzi;
    }

    public void save(ProdusComanda produsComanda) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(produsComanda);
        transaction.commit();
        session.close();
    }

    public void delete(ProdusComanda pc) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(pc);
        transaction.commit();
        session.close();
    }
}

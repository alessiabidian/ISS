package com.example.magazinonlineapp.repository.db;

import com.example.magazinonlineapp.domain.Client;
import com.example.magazinonlineapp.domain.Produs;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class ProdusDbRepository {
    static SessionFactory sessionFactory;

    public ProdusDbRepository() {
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

    public Produs findByID(Integer id) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Produs as p WHERE p.id = :id";
        Produs produs = session.createQuery(hql, Produs.class)
                .setParameter("id", id)
                .uniqueResult();
        session.close();
        return produs;
    }

    public List<Produs> findAll() {
        Session session = sessionFactory.openSession();
        List<Produs> produse = session.createQuery("FROM Produs", Produs.class).list();
        session.close();
        return produse;
    }

    public List<Produs> findByDenumirePret(String denumire, int pret) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Produs as p WHERE LOWER(p.denumire) LIKE LOWER(:denumire) AND p.pret <= :pret";
        List<Produs> produse = session.createQuery(hql, Produs.class)
                .setParameter("denumire", "%" + denumire + "%")
                .setParameter("pret", pret)
                .list();
        session.close();
        return produse;
    }

    public Produs update(Produs produs) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(produs);
        transaction.commit();
        session.close();
        return produs;
    }

    public void delete(Produs p) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(p);
        transaction.commit();
        session.close();
    }
}

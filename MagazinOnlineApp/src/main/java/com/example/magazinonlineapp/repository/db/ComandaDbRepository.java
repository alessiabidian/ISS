package com.example.magazinonlineapp.repository.db;


import com.example.magazinonlineapp.domain.Comanda;
import com.example.magazinonlineapp.domain.Produs;
import com.example.magazinonlineapp.domain.Stadiu;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class ComandaDbRepository {
    static SessionFactory sessionFactory;

    public ComandaDbRepository() {
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

    public Comanda findByID(Integer id) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Comanda as c WHERE c.id = :id";
        Comanda comanda = session.createQuery(hql, Comanda.class)
                .setParameter("id", id)
                .uniqueResult();
        session.close();
        return comanda;
    }

    public List<Comanda> findAll() {
        Session session = sessionFactory.openSession();
        List<Comanda> comenzi = session.createQuery("FROM Comanda", Comanda.class).list();
        session.close();
        return comenzi;
    }

    public void save(Comanda comanda) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(comanda);
        transaction.commit();
        session.close();
    }

    public void delete(Comanda comanda) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(comanda);
        transaction.commit();
        session.close();
    }

    public Comanda update(Comanda comanda) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(comanda);
        transaction.commit();
        session.close();
        return comanda;
    }

    public List<Comanda> findByClient(String emailclient) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Comanda as c WHERE c.emailclient = :emailclient";
        List<Comanda> comenzi = session.createQuery(hql, Comanda.class)
                .setParameter("emailclient", emailclient)
                .list();
        session.close();
        return comenzi;

    }

    public List<Comanda> findByStadiu(Stadiu stadiu) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Comanda as c WHERE c.stadiuCurent = :stadiu";
        List<Comanda> comenzi = session.createQuery(hql, Comanda.class)
                .setParameter("stadiu", stadiu)
                .list();
        session.close();
        return comenzi;
    }
}

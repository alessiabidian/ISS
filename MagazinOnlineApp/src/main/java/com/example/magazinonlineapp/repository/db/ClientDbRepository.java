package com.example.magazinonlineapp.repository.db;

import com.example.magazinonlineapp.domain.Angajat;
import com.example.magazinonlineapp.domain.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class ClientDbRepository {
    static SessionFactory sessionFactory;

    public ClientDbRepository() {
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

    public Client findByEmail(String email) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Client as c WHERE c.email = :email";
        Client client = session.createQuery(hql, Client.class)
                .setParameter("email", email)
                .uniqueResult();
        session.close();
        return client;
    }

    public List<Client> findAll() {
        Session session = sessionFactory.openSession();
        List<Client> clients = session.createQuery("FROM Client", Client.class).list();
        session.close();
        return clients;
    }

    public void save(Client client) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(client);
        transaction.commit();
        session.close();
    }

    /*public void delete(Angajat angajat) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(angajat);
        transaction.commit();
        session.close();
    }*/
}

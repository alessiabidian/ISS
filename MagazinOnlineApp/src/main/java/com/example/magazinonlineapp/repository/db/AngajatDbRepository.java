package com.example.magazinonlineapp.repository.db;

/*import com.example.magazinonlineapp.domain.Angajat;
import com.example.magazinonlineapp.domain.Client;
import com.example.magazinonlineapp.domain.validators.ValidationException;
import com.example.magazinonlineapp.repository.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AngajatDbRepository implements Repository<String, Angajat> {
    private static final Logger logger= LogManager.getLogger();

    public AngajatDbRepository(Properties props) {
        logger.info("Initializing ClientDbRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public Angajat add(Angajat elem) throws ValidationException {
//        Client c = null;
        logger.traceEntry("saving task {} ", elem);
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into angajati (nume, prenume, username, parola) values (?, ?, ?, ?)")){
            preStmt.setString(1, elem.getNume());
            preStmt.setString(2, elem.getPrenume());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);

//            c = findOne(elem);
            return elem;
        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error DB"+ex);
        }
        logger.traceExit();
//        return c;
        return null;
    }

    @Override
    public Angajat delete(String username) {
        logger.traceEntry("Deleting client with id {}", username);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement stmt = con.prepareStatement("delete from angajati WHERE email = ?")) {
            stmt.setString(1, username);

            int result = stmt.executeUpdate();
            logger.trace("Deleted {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public void update(String username, Angajat elem) {
        logger.traceEntry("Updating client with id {}", username);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement stmt = con.prepareStatement("UPDATE angajati SET nume = ?, prenume = ?, parola = ? WHERE username = ?")) {
            stmt.setString(1, elem.getNume());
            stmt.setString(2, elem.getPrenume());
            stmt.setString(3, elem.getParola());
            stmt.setString(4, username);
            int result = stmt.executeUpdate();
            logger.trace("Updated {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public List<Angajat> getAllAsList() {
        Iterable<Angajat> iterable = findAll();
        List<Angajat> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    @Override
    public Angajat findByID(String id) {
        Angajat angajat = null;

        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from angajati where username = ?")){
            preStmt.setString(1, id);
            try(ResultSet result=preStmt.executeQuery()){
                if(result.next()){
                    String nume=result.getString("nume");
                    String prenume=result.getString("prenume");
                    String parola=result.getString("parola");

                    angajat = new Angajat(nume, prenume, id, parola);
//                    client.setID(id);
                    return angajat;
                }
            }
        } catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(angajat);
        return null;
    }

    @Override
    public Angajat findOne(Angajat incompleteObject) {
        Angajat angajat = null;

        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from angajati where nume = ? and prenume = ?")){
            preStmt.setString(1, incompleteObject.getNume());
            preStmt.setString(2, incompleteObject.getPrenume());
            try(ResultSet result=preStmt.executeQuery()){
                if(result.next()){
                    String username = result.getString("username");
                    String parola = result.getString("parola");

                    angajat = incompleteObject;
                    angajat.setUsername(username);
                    angajat.setParola(parola);
                    return angajat;
                }
            }
        } catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(angajat);
        return null;
    }

    @Override
    public Iterable<Angajat> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Angajat> angajati = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from angajati")){
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    String nume=result.getString("nume");
                    String prenume=result.getString("prenume");
                    String username=result.getString("username");
                    String parola=result.getString("parola");
                    Angajat angajat = new Angajat(nume, prenume, username, parola);
                    angajati.add(angajat);
                }
            }
        } catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(angajati);
        return angajati;
    }
}*/


import com.example.magazinonlineapp.domain.Angajat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class AngajatDbRepository {
    static SessionFactory sessionFactory;

    public AngajatDbRepository() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Exceptie " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }

//        Configuration configuration = new Configuration()
//                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgresPlusDialect")
//                .setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC")
//                .setProperty("hibernate.connection.url", "jdbc:sqlite:C:\\Users\\aless\\OneDrive\\Documente\\2022 Year 2\\2023 MPP - Java\\DataBases\\issdatabase.db")
//                .setProperty("hibernate.show_sql", "true")
//                .addAnnotatedClass(Angajat.class);
//
//        // Create a Hibernate SessionFactory object based on the configuration
//        sessionFactory = configuration.buildSessionFactory();
    }

    public Angajat findByUsername(String username) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Angajat as a WHERE a.username = :username";
        Angajat angajat = session.createQuery(hql, Angajat.class)
                .setParameter("username", username)
                .uniqueResult();
        session.close();
        return angajat;
    }

//    public Angajat findByUsernameAndPassword(String username, String password) {
//        Session session = sessionFactory.openSession();
//        String hql = "FROM Angajat as a WHERE a.username = :username AND a.parola = :password";
//        Angajat angajat = session.createQuery(hql, Angajat.class)
//                .setParameter("username", username)
//                .setParameter("password", password)
//                .uniqueResult();
//        session.close();
//        return angajat;
//    }

    public List<Angajat> findAll() {
        Session session = sessionFactory.openSession();
        List<Angajat> angajati = session.createQuery("FROM Angajat", Angajat.class).list();
        session.close();
        return angajati;
    }
}

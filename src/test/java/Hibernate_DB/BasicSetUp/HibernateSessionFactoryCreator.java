package Hibernate_DB.BasicSetUp;

import Hibernate_DB.Models.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryCreator {
    private static SessionFactory sessionFactory = null;

    public static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Animal.class);
        configuration.addAnnotatedClass(Places.class);
        configuration.addAnnotatedClass(Positions.class);
        configuration.addAnnotatedClass(Sex.class);
        configuration.addAnnotatedClass(Types.class);
        configuration.addAnnotatedClass(Worker.class);
        configuration.addAnnotatedClass(Zoo.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }
}

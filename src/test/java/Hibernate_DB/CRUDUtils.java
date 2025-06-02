package Hibernate_DB;

import Hibernate_DB.BasicSetUp.HibernateSessionFactoryCreator;
import Hibernate_DB.Models.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@Slf4j
public class CRUDUtils {

    public static void insertAnimalData(Animal animal) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(animal);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public static void insertPlacesData(Hibernate_DB.Models.Places places) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(places);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public static void insertSexData(Sex sex) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(sex);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public static void insertTypeData(Types types) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(types);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public static void insertWorkerData(Worker worker) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(worker);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public static void insertZooData(Zoo zoo) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(zoo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public List<Animal> getAllAnimals() {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();

        List<Animal> animals = session
                .createQuery("FROM Animal", Animal.class)
                .getResultList();

        session.close();
        return animals;
    }

    public List<Positions> getAllPositions() {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();

        List<Positions> positions = session
                .createQuery("FROM Positions", Positions.class)
                .getResultList();

        session.close();
        return positions;
    }

    public List<Sex> getAllSex() {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();

        List<Sex> sexList = session
                .createQuery("FROM Sex", Sex.class)
                .getResultList();

        session.close();
        return sexList;
    }

    public List<Worker> getAllWorkers() {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();

        List<Worker> workers = session
                .createQuery("FROM Worker", Worker.class)
                .getResultList();

        session.close();
        return workers;
    }

    public List<Zoo> getAllZoos() {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();

        List<Zoo> zoos = session
                .createQuery("FROM Zoo", Zoo.class)
                .getResultList();

        session.close();
        return zoos;
    }

    public void updateWorker(Worker worker) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.update(worker);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void updateZoo(Zoo zoo) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.update(zoo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public int updatePlacesAndShowNumberOfAffectedRows(Places places) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int affectedRows;

        try {
            String hql = "UPDATE Places SET row = :rowValue, place_num = :placeNum, name = :nameValue WHERE id = :idValue";
            affectedRows = session.createQuery(hql)
                    .setParameter("rowValue", places.getRow())
                    .setParameter("placeNum", places.getPlace_num())
                    .setParameter("nameValue", places.getName())
                    .setParameter("idValue", places.getId())
                    .executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return affectedRows;
    }

    public void deleteTypeByID(int id) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Types type = session.get(Types.class, id);
            if (type != null) {
                session.delete(type);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void deleteSexByID(int id) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Sex sex = session.get(Sex.class, id);
            if (sex != null) {
                session.delete(sex);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public int deleteZooByIdAndShowNumberOfDeletedRows(int id) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int affectedRows;

        try {
            String hql = "DELETE FROM Zoo WHERE id = :idValue";
            affectedRows = session.createQuery(hql)
                    .setParameter("idValue", id)
                    .executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return affectedRows;
    }

    public int getEntityCount(Class<?> entityClass) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e";
            Long count = (Long) session.createQuery(hql).uniqueResult();
            return count.intValue();
        } finally {
            session.close();
        }
    }
}

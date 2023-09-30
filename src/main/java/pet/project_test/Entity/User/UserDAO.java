package pet.project_test.Entity.User;

import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pet.project_test.Exception.ExceptionUserAlreadyExists;
import pet.project_test.SessionFactoryUtil;

import java.util.Optional;

@Slf4j
public class UserDAO {
    public static void save(User user) throws ExceptionUserAlreadyExists {
        Transaction transaction = null;
        Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
        try {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
                log.info(e.getMessage());
                log.info(e.toString());
                log.info("User Exists");
                throw new ExceptionUserAlreadyExists();
            }
        } finally {
            session.close();
        }

    }


    public static Optional<User> findByLogin(String login) {
        Transaction transaction = null;
        Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
        Optional<User> userOptional = Optional.empty();
        try {
            session.beginTransaction();
            Query query = session.createQuery("from User where login = :login", User.class);
            query.setParameter("login", login);
            userOptional = Optional.ofNullable((User) query.getSingleResult());
            session.getTransaction().commit();
        } catch (RuntimeException e) {

        } finally {
            session.close();
        }

        return userOptional;
    }
}

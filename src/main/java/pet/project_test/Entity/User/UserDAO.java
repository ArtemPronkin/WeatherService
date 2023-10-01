package pet.project_test.Entity.User;

import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pet.project_test.Entity.EntityDAO;
import pet.project_test.SessionFactoryUtil;


import java.util.Optional;

@Slf4j
public class UserDAO extends EntityDAO<User> {
    public  Optional<User> findByLogin(String login) {
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

package pet.project.Entity;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;
import pet.project.Exception.ExceptionUserAlreadyExists;
import pet.project.SessionFactoryUtil;

import java.util.Optional;
import java.util.UUID;

@Slf4j
public class SessionDAO {
    public static void save(Session sessionUser) {
        Transaction transaction = null;
        org.hibernate.Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
        try {
            transaction = session.beginTransaction();
            session.persist(sessionUser);
            transaction.commit();
        } catch (RuntimeException e) {

            if (transaction != null) {
                transaction.rollback();
                log.error("SessionDAO not save session");
                log.error(e.toString());
            }
        } finally {
            session.close();
        }
    }

    public static Optional<Session> getById(UUID id) {
        org.hibernate.Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
        var transaction = session.beginTransaction();
        var optionalSession = Optional.ofNullable(session.get(Session.class, id));
        session.getTransaction().commit();
        return optionalSession;
    }
}

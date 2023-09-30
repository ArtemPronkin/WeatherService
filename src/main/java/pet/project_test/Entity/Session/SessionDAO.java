package pet.project_test.Entity.Session;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;
import pet.project_test.SessionFactoryUtil;

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
        Optional<Session> optionalSession = Optional.empty();
        org.hibernate.Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
        try {
            var transaction = session.beginTransaction();
            optionalSession = Optional.ofNullable(session.get(Session.class, id));

        } finally {
            session.close();
        }
        return optionalSession;
    }
}

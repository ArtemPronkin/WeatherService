package pet.project_test.entity.session;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;
import pet.project_test.entity.EntityDAO;
import pet.project_test.SessionFactoryUtil;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class SessionDAO extends EntityDAO<Session> {

    public Optional<Session> getById(UUID id) {
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

    public void deleteExpiredSessions(LocalDateTime time) {
        Transaction transaction = null;
        org.hibernate.Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
        try {
            transaction = session.beginTransaction();
            session.createQuery("delete from Session where ExpiresAt <= :time")
                    .setParameter("time", time)
                    .executeUpdate();
            transaction.commit();
            log.info("deleteExpiredSessions is run");
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
                log.info("deleteExpiredSessions is ex");
                log.info(e.getMessage());
                log.info(e.toString());
                throw e;
            }
        } finally {
            session.close();
        }
    }
}

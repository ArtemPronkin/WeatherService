package pet.project_test.Entity;

import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pet.project_test.SessionFactoryUtil;
@Slf4j
public abstract class EntityDAO<T> {
    public void save(T entity) throws PersistenceException {
        Transaction transaction = null;
        Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
        try {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
                log.info(e.getMessage());
                throw e;
            }
        } finally {
            session.close();
        }
    }
    public void delete(T entity){
        Transaction transaction = null;
        Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
        try {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
                log.warn(e.getMessage());
                throw e;

            }
        } finally {
            session.close();
        }
    }
}

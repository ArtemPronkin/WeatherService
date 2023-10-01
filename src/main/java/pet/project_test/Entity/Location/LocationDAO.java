package pet.project_test.Entity.Location;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pet.project_test.Entity.EntityDAO;
import pet.project_test.SessionFactoryUtil;
@Slf4j
public class LocationDAO extends EntityDAO<Location> {

    public void deleteForId(Integer id) {
        Transaction transaction = null;
        Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
        try {
            transaction = session.beginTransaction();
            session.createQuery("delete from Location where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
                log.info(e.getMessage());
                log.info(e.toString());
                throw e;
            }
        } finally {
            session.close();
        }
    }

}

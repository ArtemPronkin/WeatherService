package pet.project_test.entity.location;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pet.project_test.entity.EntityDAO;
import pet.project_test.entity.user.User;
import pet.project_test.SessionFactoryUtil;

import java.math.BigDecimal;

@Slf4j
public class LocationDAO extends EntityDAO<Location> {
    public void deleteByUser(User user, BigDecimal lat, BigDecimal lon) {
        Transaction transaction = null;
        Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
        try {
            transaction = session.beginTransaction();
            session.createQuery("delete from Location where user=:user AND latitide=:lat AND longitide=:lon")
                    .setParameter("lat", lat)
                    .setParameter("lon", lon)
                    .setParameter("user", user)
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

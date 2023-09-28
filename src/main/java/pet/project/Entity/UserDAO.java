package pet.project.Entity;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pet.project.Exception.ExceptionUserAlreadyExists;
import pet.project.SessionFactoryUtil;

@Slf4j
public class UserDAO {
    public static void save (User user) throws ExceptionUserAlreadyExists {
        Transaction transaction = null;
        Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
        try {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        }catch (RuntimeException e){
            if (transaction!=null){
                transaction.rollback();
                log.info("User уже есть в таблице");
                throw new ExceptionUserAlreadyExists();
            }
        }finally {
            session.close();
        }

    }
}

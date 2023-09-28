package pet.project;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.Configuration;
import pet.project.Entity.Location;
import pet.project.Entity.Session;
import pet.project.Entity.User;
@Slf4j
public class SessionFactoryUtil {
    private org.hibernate.SessionFactory sessionFactory;

    private SessionFactoryUtil() {
        Configuration configuration = new Configuration().configure("hibernateH2.cfg.xml");
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Session.class);
        configuration.addAnnotatedClass(Location.class);
        this.sessionFactory = configuration.buildSessionFactory();
    }

    private static class SingletonHolder {
        public static final SessionFactoryUtil HOLDER_INSTANCE = new SessionFactoryUtil();
    }

    public static org.hibernate.SessionFactory getSessionFactory() {
        log.info("Выдана сессия");
        return SingletonHolder.HOLDER_INSTANCE.sessionFactory;
    }

}

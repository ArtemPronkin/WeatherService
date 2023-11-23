package pet.project_test;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.Configuration;
import pet.project_test.entity.location.Location;
import pet.project_test.entity.session.Session;
import pet.project_test.entity.user.User;

@Slf4j
public class SessionFactoryUtil {
    private org.hibernate.SessionFactory sessionFactory;

    private SessionFactoryUtil() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Session.class);
        configuration.addAnnotatedClass(Location.class);
        this.sessionFactory = configuration.buildSessionFactory();
    }

    private static class SingletonHolder {
        public static final SessionFactoryUtil HOLDER_INSTANCE = new SessionFactoryUtil();
    }

    public static org.hibernate.SessionFactory getSessionFactory() {
        log.info("getSessionFactory");
        return SingletonHolder.HOLDER_INSTANCE.sessionFactory;
    }

}

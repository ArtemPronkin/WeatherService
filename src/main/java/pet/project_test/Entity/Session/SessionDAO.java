package pet.project_test.Entity.Session;

import lombok.extern.slf4j.Slf4j;
import pet.project_test.Entity.EntityDAO;
import pet.project_test.SessionFactoryUtil;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class SessionDAO extends EntityDAO<Session> {

    public  Optional<Session> getById(UUID id) {
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

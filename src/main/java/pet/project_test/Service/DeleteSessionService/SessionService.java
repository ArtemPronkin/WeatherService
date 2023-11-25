package pet.project_test.Service.DeleteSessionService;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import pet.project_test.entity.session.SessionDAO;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@WebListener
public class SessionService implements ServletContextListener {
    ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> scheduledFuture;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Session clear service Initialized");
        SessionDAO sessionDAO = new SessionDAO();
        Runnable task = () -> sessionDAO.deleteExpiredSessions(LocalDateTime.now());
        scheduledFuture = ses.scheduleAtFixedRate(task, 0, 24, TimeUnit.HOURS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduledFuture.cancel(true);
        ses.shutdownNow();
        log.info("Session clear service close");
    }


}

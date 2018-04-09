
import javax.persistence.EntityManager;

import models.dao.AbstractDAO;
import models.dao.ApplicationDAO;

import org.junit.After;
import org.junit.Before;

import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.test.*;
import scala.Option;

public abstract class AbstractTest {
    public EntityManager em;
    public AbstractDAO<Object> dao;

    @Before
    public void setUp() {
        FakeApplication app = Helpers.fakeApplication(new Global());
        Helpers.start(app);
        Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(JPAPlugin.class);
        em = jpaPlugin.get().em("default");
        JPA.bindForCurrentThread(em);
        em.getTransaction().begin();
        dao = new ApplicationDAO();
    }

    @After
    public void tearDown() {
        em.getTransaction().commit();
        JPA.bindForCurrentThread(null);
        em.close();
    }
}
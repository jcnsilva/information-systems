import models.Episode;
import models.csv.FileHandler;
import models.TVShow;
import play.*;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.db.jpa.JPA;

import java.util.List;

public class Global extends GlobalSettings {
    public void onStart(Application app) {
        JPA.withTransaction(new play.libs.F.Callback0() {
            @Override
            public void invoke() throws Throwable {
               FileHandler.read();
            }
        });
    }

    public void onStop(Application app) {
        Logger.info("Aplicação desligada...");
    }
}

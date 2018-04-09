import java.util.GregorianCalendar;

import play.*;
import models.Meta;
import models.Semana;
import models.Meta.Prioridade;
import models.DAO.DAO;
import play.db.jpa.JPA;
import java.util.List;

public class Global extends GlobalSettings {

    private static DAO dao = new DAO();
    private Semana semana1, semana2, semana3, semana4, semana5, semana6;

    @Override
    public void onStart(Application app) {
        Logger.info("Aplicação inicializada...");

        JPA.withTransaction(new play.libs.F.Callback0() {
        	
            @Override
            public void invoke() throws Throwable {
            	GregorianCalendar dataControle = new GregorianCalendar();
                semana1 = new Semana(dataControle);
                dao.persist(semana1);
                dao.flush();
                
                dataControle.add(GregorianCalendar.DAY_OF_MONTH, 7);
                semana2 = new Semana(dataControle);
                dao.persist(semana2);
                dao.flush();
                
                dataControle.add(GregorianCalendar.DAY_OF_MONTH, 7);
                semana3 = new Semana(dataControle);
                dao.persist(semana3);
                dao.flush();
                
                dataControle.add(GregorianCalendar.DAY_OF_MONTH, 7);
                semana4 = new Semana(dataControle);
                dao.persist(semana4);
                dao.flush();
                
                dataControle.add(GregorianCalendar.DAY_OF_MONTH, 7);
                semana5 = new Semana(dataControle);
                dao.persist(semana5);
                dao.flush();
                
                dataControle.add(GregorianCalendar.DAY_OF_MONTH, 7);
                semana6 = new Semana(dataControle);
                dao.persist(semana6);
                dao.flush();
                
                semana1.addMeta(new Meta("Atividade 1", "Minha atividade 1", Prioridade.ALTA));
                dao.merge(semana1);
                dao.flush();     
                
                semana1.addMeta(new Meta("Atividade 2", "Minha atividade 2", Prioridade.NORMAL));
                dao.merge(semana1);
                dao.flush();    
                
                semana1.addMeta(new Meta("Atividade 3", "Minha atividade 3", Prioridade.BAIXA));
                dao.merge(semana1);
                dao.flush();    
                
                semana1.addMeta(new Meta("Atividade 4", "Minha atividade 4", Prioridade.ALTA));
                dao.merge(semana1);
                dao.flush();    
                
                semana2.addMeta(new Meta("Atividade 5", "Minha atividade 5", Prioridade.ALTA));
                dao.merge(semana2);
                dao.flush();    
                
                semana2.addMeta(new Meta("Atividade 6", "Minha atividade 6", Prioridade.BAIXA));
                dao.merge(semana2);
                dao.flush();    
                
                semana3.addMeta(new Meta("Atividade 7", "Minha atividade 7", Prioridade.ALTA));
                dao.merge(semana3);
                dao.flush();    
                
                semana4.addMeta(new Meta("Atividade 8", "Minha atividade 8", Prioridade.NORMAL));
                dao.merge(semana4);
                dao.flush();    
                
                semana5.addMeta(new Meta("Atividade 9", "Minha atividade 9", Prioridade.NORMAL));
                dao.merge(semana5);
                dao.flush();    
                
                semana6.addMeta(new Meta("Atividade 10", "Minha atividade 10", Prioridade.BAIXA)); 
                dao.merge(semana6);
                dao.flush();  
            }
        });
    }
    
    
    @Override
    public void onStop(Application app) {
        Logger.info("Aplicação finalizada...");

        JPA.withTransaction(new play.libs.F.Callback0() {
        	
            @Override
            public void invoke() throws Throwable {
            	List<Semana> semanas = dao.findAllByClass(Semana.class);
            	
            	for(Semana semana : semanas){
            		dao.remove(semana);
                	dao.flush();
            	} 
            }
        });
    }
}

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import models.dao.ApplicationDAO;
import models.Tema;
import play.*;
import play.db.jpa.JPA;
import play.libs.F.Callback0;

public class Global extends GlobalSettings{
	
	private ApplicationDAO dao = new ApplicationDAO();
	private List<Tema> temasDefault = new LinkedList<Tema>();
	
	public void onStart(Application app){
		JPA.withTransaction(new Callback0() {
            @Override
            public void invoke() throws Throwable {
            	Logger.info("Aplicacao inicializada - adicionando temas ao sistema");
            	
            	if(dao.getAllByClass(Tema.class).isEmpty()){
	                temasDefault.add(new Tema("Análise e Design"));
	            	temasDefault.add(new Tema("Orientação a Objetos"));
	            	temasDefault.add(new Tema("Padrões GRASP"));
	            	temasDefault.add(new Tema("Gang of Four - GoF"));
	            	temasDefault.add(new Tema("Arquitetura de Software"));
	            	temasDefault.add(new Tema("Play Framework"));
	            	temasDefault.add(new Tema("Javascript"));
	            	temasDefault.add(new Tema("Html, Css e Bootstrap"));
	            	temasDefault.add(new Tema("Heroku"));
	            	temasDefault.add(new Tema("Laboratórios"));
	               	temasDefault.add(new Tema("Minitestes"));
	            	temasDefault.add(new Tema("Projeto"));
	               
	            	Iterator<Tema> it = temasDefault.iterator();
	            	while(it.hasNext()){
	            	   dao.persist(it.next());
	               }
	            	
	            	dao.flush();
            	}
            }
        });
	}
	
	public void onStop(Application app){
		Logger.info("Aplicacao finalizada");
	}
}

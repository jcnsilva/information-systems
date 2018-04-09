package controllers;

import models.DAO.DAO;
import models.Meta;
import models.Semana;
import play.*;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;
import play.Logger;
import views.html.*;

import java.util.GregorianCalendar;
import java.util.List;

public class Application extends Controller {

	
    private static final DAO meuDAO = new DAO();
    private static Form<Meta> metas = Form.form(Meta.class);
	
       
    public static Result index() {
        return redirect(routes.Application.metas());
    }
    
    

    @Transactional
    public static Result metas(){
        List<Semana> semanas = meuDAO.findAllByClass(Semana.class);
        return ok(views.html.index.render(semanas));
    }

    
    
    @Transactional
    public static Result addMeta() {
        Form<Meta> formulario = metas.bindFromRequest();

        if (formulario.hasErrors()) {
        	
        	List<Semana> semanas = meuDAO.findAllByClass(Semana.class);
        	return badRequest(views.html.index.render(semanas));
        	
        } else {
        	
            Meta novaMeta = new Meta();
            novaMeta.setNome(formulario.field("nome").value());
            novaMeta.setDescricao(formulario.field("descricao").value());
            novaMeta.setPrioridade(formulario.field("prioridade").value());
            
            Long semanaId = Long.parseLong(formulario.field("semana").value());
            
            Semana semana;
            semana = meuDAO.findByEntityId(Semana.class, semanaId);
            
            if(semana == null){
            	
            	String semanaIdString = formulario.field("semana").value();
            	int dia = Integer.parseInt(semanaIdString.substring(0, 2));
            	int mes = Integer.parseInt(semanaIdString.substring(2, 4)) - 1;
            	int ano = Integer.parseInt(semanaIdString.substring(4, 8));
            	semana = new Semana(new GregorianCalendar(ano, mes, dia));
            	semana.addMeta(novaMeta);
            	Logger.debug("criada semana" + semana.intervalAsString());
            	meuDAO.persist(semana);
            	
            } else {
            	semana.addMeta(novaMeta);
                meuDAO.merge(semana);
            }
            
            meuDAO.flush();

            Logger.debug("Adicionada meta " + novaMeta.getNome() + " na semana " + semana.intervalAsString());
            
        }
        
        return redirect(routes.Application.metas());
    }
    
    

    @Transactional
    public static Result deleteMeta(Long semanaId, Long metaId){
    	Semana semana = meuDAO.findByEntityId(Semana.class, semanaId);
        Meta metaDesejada = meuDAO.findByEntityId(Meta.class, metaId);

        semana.deleteMeta(metaDesejada);
        
        Logger.debug("Removendo meta:" + metaDesejada.getNome());
        meuDAO.remove(metaDesejada); //Ao dar merge em semana, não deveria remover automaticamente?
        meuDAO.merge(semana);
        meuDAO.flush();

        return redirect(routes.Application.metas());
    }
    
    

    @Transactional
    public static Result setAlcancada(Long id){
        Meta metaDesejada = meuDAO.findByEntityId(Meta.class, id);
        metaDesejada.setAlcancada(true);

        meuDAO.merge(metaDesejada);
        meuDAO.flush();

        return redirect(routes.Application.metas());
    }
}

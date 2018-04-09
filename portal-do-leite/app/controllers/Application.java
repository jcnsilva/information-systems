package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.*;
import models.Tema.Dificuldade;
import models.dao.*;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	
	private static ApplicationDAO dao = new ApplicationDAO();
	private static Form<Tema> form = Form.form(Tema.class);
	private static Form<Opiniao> formOpiniao = Form.form(Opiniao.class);
	
    public static Result index() {
        return redirect(routes.Application.home());
    }
    
    public static Result login() {
    	return ok(login.render());
    }
    
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result home() {
    	List<Tema> temas = dao.getAllByClass(Tema.class);
    	List<Dica> dicas = dao.getAllByClass(Dica.class);
    	Collections.sort(dicas);
    	
    	return ok(home.render(temas, dicas));
    }
    
    public static Result register() {
    	return ok(register.render());
    }
    
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result details(Long idTema){
    	Tema temaSelecionado = dao.getElementById(Tema.class, idTema);
    	return ok(details.render(temaSelecionado));
    }
    
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result addAvaliacao(Long idTema){
    	Tema temaSelecionado = dao.getElementById(Tema.class, idTema);
    	Form<Tema> tema = form.bindFromRequest();
    	
    	if(tema.hasErrors()){
    		return badRequest(details.render(temaSelecionado));
    	} else {
	    	String aval = tema.field("avaliacao").value(); 
	    	String username = session().get("username");
	    	Aluno aluno = dao.getByAttributeName(Aluno.class, "username", username).get(0);
	    	
	    	switch(aval){
	    	case "muitoFacil": 
	    		temaSelecionado.addAvaliacao(aluno, Dificuldade.MUITO_FACIL);
	    		break;
	    	case "facil": 
	    		temaSelecionado.addAvaliacao(aluno, Dificuldade.FACIL);
	    		break;
	    	case "medio":
	    		temaSelecionado.addAvaliacao(aluno, Dificuldade.MEDIO);
	    		break;
	    	case "dificil": 
	    		temaSelecionado.addAvaliacao(aluno, Dificuldade.DIFICIL);
	    		break;
	    	case "muitoDificil": 
	    		temaSelecionado.addAvaliacao(aluno, Dificuldade.MUITO_DIFICIL);
	    		break;
	    	}
    	
	    	dao.merge(temaSelecionado);
	    	dao.flush();
	    	return ok(details.render(temaSelecionado));
    	}
    	
    }
    
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result addDica(Long idTema, String tipo){
    	Tema temaSelecionado = dao.getElementById(Tema.class, idTema);
    	Form<Tema> tema = form.bindFromRequest();
    	
    	if(tema.hasErrors()){
    		return badRequest(details.render(temaSelecionado));
    	} else {
	    	String username = session().get("username");
	    	Aluno aluno = dao.getByAttributeName(Aluno.class, "username", username).get(0);
	    	
	    	switch(tipo){
	    	case "assunto": 
	    		String assunto = tema.field("assunto").value();
	    		temaSelecionado.addDica(new DicaAssunto(aluno, assunto));
	    		break;
	    	case "disciplina": 
	    		String disciplina = tema.field("disciplina").value();
	    		String razao = tema.field("razao").value();
	    		temaSelecionado.addDica(new DicaDisciplina(aluno, disciplina, razao));
	    		break;
	    	case "linkMaterial":
	    		String link = tema.field("linkMaterial").value();
	    		temaSelecionado.addDica(new DicaMaterial(aluno, link));
	    		break;
	    	case "conselho":
	    		String conselho = tema.field("conselho").value();
	    		temaSelecionado.addDica(new DicaConselho(aluno, conselho));
	    		break;
	    	}
    	
	    	dao.merge(temaSelecionado);
	    	dao.flush();
	    	return ok(details.render(temaSelecionado));
    	}
    }
    
    //Candidato em potencial para refatoramento
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result addMetadica(){
    	DynamicForm form = Form.form().bindFromRequest();
    	
    	if(form.hasErrors()){
    		return redirect(routes.Application.home());
    	} else {
    		Map<String, String> dados = form.data();
        	
        	MetaDica metadica = new MetaDica();
        	
        	//Adiciona as dicas na metadica criada
        	Iterator<String> it = dados.keySet().iterator();
        	int indiceAtual = 0;
        	while(it.hasNext()){
        		String chaveAtual = it.next();
        		
        		if(chaveAtual.startsWith("selecionarDicas")){
        			Long idDica = Long.parseLong(dados.get("selecionarDicas[" + indiceAtual +"]"));
        			Dica dicaCorrespondente = dao.getElementById(Dica.class, idDica);
        			metadica.addDica(dicaCorrespondente);
        			indiceAtual++;
        		}
        		
        	}
        	
        	dao.persist(metadica); 
        	dao.flush();
        	return redirect(routes.Application.home());
    	}
    }
    
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result addConcordancia(Long idTema, Long idDica){
    	Tema temaDesejado = dao.getElementById(Tema.class, idTema);
    	Dica dicaDesejada = dao.getElementById(Dica.class, idDica);
    	String username = session().get("username");
    	Aluno aluno = dao.getByAttributeName(Aluno.class, "username", username).get(0);
    	
    	Opiniao novaOpiniao = new Opiniao(true, "");
    	dao.persist(novaOpiniao);
    	dao.flush();
    	
    	dicaDesejada.addOpiniao(aluno, novaOpiniao);
    	dao.merge(dicaDesejada);
    	dao.flush();
    	
    	return ok(details.render(temaDesejado));
    }
    
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result addDiscordancia(Long idTema, Long idDica){
    	Tema temaDesejado = dao.getElementById(Tema.class, idTema);
    	Form<Opiniao> opn = formOpiniao.bindFromRequest();
    	String textoDiscordancia = opn.field("textoDiscordancia").value();
    	
    	if(opn.hasErrors()){
    		return badRequest(details.render(temaDesejado));
    	} else {
    		Dica dicaDesejada = dao.getElementById(Dica.class, idDica);
	    	String username = session().get("username");
	    	Aluno aluno = dao.getByAttributeName(Aluno.class, "username", username).get(0);
	    	
	    	Opiniao novaOpiniao = new Opiniao(false, textoDiscordancia);
	    	dao.persist(novaOpiniao);
	    	dao.flush();
	    	
	    	dicaDesejada.addOpiniao(aluno, novaOpiniao);	
	    	dao.merge(dicaDesejada);
	    	dao.flush();
	    	
	    	return ok(details.render(temaDesejado));
    	}
    	
    }
    
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result reportar(Long idTema, Long idDica){
    	Tema temaDesejado = dao.getElementById(Tema.class, idTema);
    	Dica dicaDesejada = dao.getElementById(Dica.class, idDica);
    	String username = session().get("username");
    	Aluno aluno = dao.getByAttributeName(Aluno.class, "username", username).get(0);
    	
    	/*Se a dica já foi reportada duas vezes, um terceiro report causa a exclusao da mesma.
    	Logo não é necessario adicionar o novo report e fazer o merge da dica, basta exclui-la diretamente*/
    	if(dicaDesejada.getTotalReports() >= 2){
    		dao.remove(dicaDesejada);
    	}else{
    		dicaDesejada.addReportador(aluno);
        	dao.merge(dicaDesejada);
    	}
    	
    	dao.flush();
    	
    	return ok(details.render(temaDesejado));
    }
    
}

package controllers;

import java.util.List;

import models.Aluno;
import models.dao.ApplicationDAO;
import play.Logger;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Auth extends Controller{
	
	private static ApplicationDAO dao = new ApplicationDAO();
	private static Form<Aluno> form = Form.form(Aluno.class);
	
	@Transactional
	public static Result registrar(){
		Aluno aluno = form.bindFromRequest().get();
		
		if(form.hasErrors()){
			flash("fail");
			return badRequest(register.render());
		} else {			
			dao.persist(aluno);
			return ok(login.render());
		}
	}
	
	
	@Transactional
	public static Result logar(){
		Form<Aluno> aluno = form.bindFromRequest();
		String username = aluno.field("username").value();
		
		if(aluno.hasErrors() || !validar(username, aluno.field("senha").value())){
			flash("fail");
			return badRequest(login.render());
		} else {
			Aluno alunoAtual = dao.getByAttributeName(Aluno.class, "username", username).get(0);
			session().clear();
			session("user", alunoAtual.getNome());
			session().put("username", alunoAtual.getUsername());
			return redirect(routes.Application.home());
		}
	}
	
	
	@Transactional
	public static Result deslogar() {
		session().clear();
		return redirect(routes.Application.login());
	}
	
	@Transactional
	private static boolean validar(String username, String senha) {
		List<Aluno> aluno = dao.getByAttributeName(Aluno.class, "username", username);
		
		if (aluno == null || aluno.isEmpty() ||
			!aluno.get(0).getSenha().equals(senha))
			return false;
		else
			return true;
	}
}

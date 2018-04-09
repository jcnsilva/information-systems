import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import models.Aluno;
import models.Tema;
import models.dao.ApplicationDAO;

import org.junit.*;

import play.GlobalSettings;
import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;
import scala.Option;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import static play.mvc.Controller.*;


public class ApplicationTest extends AbstractTest{

	private Result result;
	
	@Test
    public void testaAddAvaliacao() {
    	Map<String, String> fakeForm = new HashMap<>();
    	fakeForm.put("avaliacao", "dificil");
    	
    	Aluno aluno = new Aluno("Teste", "teste", "teste");
    	dao.persist(aluno);
    	dao.flush();
    	dao.commitAndContinue();
    	
    	Tema assunto = dao.getByAttributeName(Tema.class, "nome", "Projeto").get(0);
    	
    	result = callAction(controllers.routes.ref.Application.addAvaliacao(assunto.getId()) , 
    				fakeRequest().withSession("username", aluno.getUsername())
    							.withSession("nome", aluno.getNome())
    							.withFormUrlEncodedBody(fakeForm));
    	
    	dao.refresh(assunto);
    	
    	assertThat(status(result)).isEqualTo(Http.Status.OK);
    	assertThat(assunto.totalAvaliacoes()).isEqualTo(1);
    }
	
	@Test
	public void testaAddDica(){
		Map<String, String> fakeForm = new HashMap<>();
		fakeForm.put("conselho", "Comece logo!!");
		
		Aluno aluno = new Aluno("Teste", "teste", "teste");
    	dao.persist(aluno);
    	dao.flush();
    	dao.commitAndContinue();
    	
    	Tema assunto = dao.getByAttributeName(Tema.class, "nome", "Projeto").get(0);
    	
    	result = callAction(controllers.routes.ref.Application.addDica(assunto.getId(), "conselho"), 
				fakeRequest().withSession("username", aluno.getUsername())
					.withSession("nome", aluno.getNome())
					.withFormUrlEncodedBody(fakeForm));
    	
    	dao.refresh(assunto);
    	
    	assertThat(status(result)).isEqualTo(Http.Status.OK);
    	assertThat(assunto.totalDicas()).isEqualTo(1);
	}


}

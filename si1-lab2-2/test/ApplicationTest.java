import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.redirectLocation;
import static play.test.Helpers.status;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import models.Meta;
import models.Semana;
import models.DAO.DAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.mvc.Http;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;
import scala.Option;

public class ApplicationTest {

    public Result result;
    public EntityManager em;
    public DAO dao;
    
    @Before
    public void setUp(){
        FakeApplication app = Helpers.fakeApplication(new GlobalSettings());
        Helpers.start(app);
        
        Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(JPAPlugin.class);
        
        em = jpaPlugin.get().em("default");
        JPA.bindForCurrentThread(em);
        em.getTransaction().begin();
        
        dao = new DAO();
    }
    
    

    @After
    public void tearDown() {
    	em.getTransaction().commit();
        JPA.bindForCurrentThread(null);
        em.close();
    }
    
    

    @Test
    public void deveRedirecionar(){
        result = callAction(controllers.routes.ref.Application.index(),fakeRequest());
        assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo("/metas");
    }

    @Test
    public void deveAdicionarMetas(){
    	//Cria formulário falso
        Map<String, String> fakeForm = new HashMap<String, String>();
        fakeForm.put("nome", "Lab 2 de SI");
        fakeForm.put("descricao", "Laboratório usando play! e Java");
        fakeForm.put("prioridade", "alta");
        fakeForm.put("semana", "3011201406122014");
        
        //Chama a ação addMeta() usando o formulário falso
        result = callAction(controllers.routes.ref.Application.addMeta(),
                            fakeRequest().withFormUrlEncodedBody(fakeForm));

        assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo("/metas");
        
        //Verifica se a meta foi adicionada ao BD
        List<Meta> metas = dao.findAllByClass(Meta.class);
        assertThat(metas.size()).isEqualTo(1);
        assertThat(metas.get(0).getNome()).isEqualTo("Lab 2 de SI");
        List<Meta> result2 = dao.findByAttributeName("Meta",
                "nome", "Lab 2 de SI");
        assertThat(result2.size()).isEqualTo(1);
        

        result = callAction(controllers.routes.ref.Application.metas(),
                fakeRequest());
        assertThat(status(result)).isEqualTo(Http.Status.OK);
        assertThat(contentAsString(result)).contains("Lab 2 de SI");
    }
    
    
    
    @Test
    public void deveRemoverMetas(){
    	
    	//Cria formulário falso
    	Map<String, String> fakeForm = new HashMap<String, String>();
        fakeForm.put("nome", "Lab 2 de SI");
        fakeForm.put("descricao", "Laboratório usando play! e Java");
        fakeForm.put("prioridade", "alta");
        fakeForm.put("semana", "3011201406122014");
                
        //Chama a ação addMeta() usando o formulário falso
        callAction(controllers.routes.ref.Application.addMeta(),
                   fakeRequest().withFormUrlEncodedBody(fakeForm));
        
        //Remove a meta criada pelo formulário falso
        Meta desejada = (Meta) dao.findByAttributeName("Meta", "nome", "Lab 2 de SI").get(0);
        Long idMeta = desejada.getId();
        result = callAction(controllers.routes.ref.Application.deleteMeta(3011201406122014L, idMeta));
        
        //Verifica se a meta foi removida do BD
        List<Meta> metas = dao.findAllByClass(Meta.class);
        assertThat(metas.size()).isEqualTo(0);
        
        //Verifica se o redirecionamento foi realizado com sucesso
        assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo("/metas");
        
        //Verifica se a meta foi removida da view
        result = callAction(controllers.routes.ref.Application.metas(),
        					fakeRequest());
        assertThat(status(result)).isEqualTo(Http.Status.OK);
        assertThat(contentAsString(result)).doesNotContain("Lab 2 de SI");     
    }
    
    @Test
    public void deveConfigurarComoAlcancada(){
    	
    	//Cria formulário falso
    	Map<String, String> fakeForm = new HashMap<String, String>();
        fakeForm.put("nome", "Lab 2 de SI");
        fakeForm.put("descricao", "Laboratório usando play! e Java");
        fakeForm.put("prioridade", "alta");
        fakeForm.put("semana", "3011201406122014");
        
        //Chama a ação addMeta() usando o formulário falso
        callAction(controllers.routes.ref.Application.addMeta(),
                   fakeRequest().withFormUrlEncodedBody(fakeForm));
        
        //Configura uma meta como alcançada e atualiza o valor da variavel local
        Meta metaDesejada = (Meta) dao.findAllByClass(Meta.class).get(0);
        Long idMeta = metaDesejada.getId();
        callAction(controllers.routes.ref.Application.setAlcancada(idMeta));
        dao.refresh(metaDesejada);        
        
        //Verifica se a meta desejada foi configurada como alcançada no BD
        assertThat(metaDesejada.getAlcancada()).isTrue();
    }
    
    
	
}

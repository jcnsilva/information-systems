import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.callAction;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.redirectLocation;
import static play.test.Helpers.status;

import java.util.List;

import models.Episodio;
import models.Serie;
import models.Temporada;

import org.junit.Test;

import play.db.jpa.Transactional;
import play.mvc.Http;
import play.mvc.Result;


public class ApplicationTest extends AbstractTest{
	
	private Result result;
	
	@Test
	public void deveRedirecionar(){
		result = callAction(controllers.routes.ref.Application.index(), fakeRequest());
		assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
    	assertThat(redirectLocation(result)).isEqualTo("/series");
	}

    @Test
    public void deveConfigurarAssistindo(){
    	Serie novaSerie = new Serie("Family Guy");
    	dao.persist(novaSerie);
    	dao.commitAndContinue();
    	
    	List<Serie> series = dao.findByAttributeName("Serie", "nome", "Family Guy");
    	Serie serieProcurada = series.get(0);
    	Long idSerie = serieProcurada.getId();
    	
    	result = callAction(controllers.routes.ref.Application.setAssistindo(idSerie), fakeRequest());
    	assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
    	assertThat(redirectLocation(result)).isEqualTo("/series");
    	
    	dao.refresh(serieProcurada);
    	assertThat(serieProcurada.isAssistindo());
    }
    
    @Test
    public void deveConfigurarEpiAssistido() throws Exception{
    	Serie novaSerie = new Serie("Chuck");
    	Temporada novaTemporada = new Temporada(1, novaSerie);
    	Episodio novoEpisodio = new Episodio("Pilot", 1, novaTemporada);
    	
    	novaTemporada.addEpisodio(novoEpisodio);
    	novaSerie.addTemporada(novaTemporada);
    	
    	dao.persist(novaSerie);
    	dao.commitAndContinue();
    	
    	List<Episodio> episodios = dao.findByAttributeName("Episodio", "nome", "Pilot");
    	Episodio episodioProcurado = episodios.get(0);
    	Long idEpisodio = episodioProcurado.getId();
    	
    	result = callAction(controllers.routes.ref.Application.setEpisodioAssistido(idEpisodio), fakeRequest());
    	assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
    	assertThat(redirectLocation(result)).isEqualTo("/minhasseries");
    	
    	dao.refresh(episodioProcurado);
    	assertThat(episodioProcurado.isAssistido());
    	
    	
    }

}

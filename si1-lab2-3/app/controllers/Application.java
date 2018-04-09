/*
 * Configurar o activator no ubuntu: 
 * export PATH=$PATH:/home/juliocns/Documentos/activator-1.2.10-minimal 
 */

package controllers;

import java.util.List;

import models.Episodio;
import models.Serie;
import models.DAO.DAO;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.myseries;


public class Application extends Controller {
	
	private static DAO dao = new DAO();

	@Transactional
    public static Result index() {
    	return redirect(routes.Application.series());
    }
	
	@Transactional
	public static Result series(){
		List<Serie> seriesSistema = dao.findByAttributeName("Serie", "status", "NAO_ASSISTIDA");
    	
        return ok(index.render(seriesSistema));
	}
	
	@Transactional
	public static Result minhasSeries(){
		List<Serie> minhasSeries = dao.findByAttributeName("Serie", "status", "ASSISTINDO");
		List<Serie> seriesAssistidas = dao.findByAttributeName("Serie", "status", "ASSISTIDA");
		minhasSeries.addAll(seriesAssistidas);
		
		return ok(myseries.render(minhasSeries));
	}
	
	@Transactional
	public static Result setAssistindo(Long idSerie){
		Serie serieProcurada = dao.findByEntityId(Serie.class, idSerie);
		serieProcurada.setAssistindo();
		dao.persist(serieProcurada);
		dao.flush();
		
		return redirect(routes.Application.series());
	}
	
	@Transactional
	public static Result setEpisodioAssistido(Long idEpisodio){
		Episodio episodioProcurado = dao.findByEntityId(Episodio.class, idEpisodio);
		episodioProcurado.setAssistido(true);
		dao.persist(episodioProcurado);
		dao.flush();
		
		return redirect(routes.Application.minhasSeries());
	}
	

}

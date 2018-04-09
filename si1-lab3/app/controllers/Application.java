package controllers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import models.Episode;
import models.Season;
import models.TVShow;
import models.calculator.NewerEpisodeCalculator;
import models.calculator.OlderEpisodeCalculator;
import models.calculator.PastThreeEpisodeCalculator;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

    private static GenericDAO dao = new GenericDAOImpl();
    private static Form<TVShow> shows = Form.form(TVShow.class);

    @Transactional
    public static Result index() {
        List<TVShow> tvShows;
        tvShows = dao.findAllByClassName(TVShow.class.getName());
        List<TVShow> watching = new LinkedList<TVShow>();
        List<TVShow> unWatched = new LinkedList<TVShow>();

        Collections.sort(tvShows);
        
        for (TVShow show: tvShows) {
        	if(!show.isFollowing()) {
                unWatched.add(show);
            } else {
                watching.add(show);
            }
        	
        	Collections.sort(show.getSeasons());
        	
            for(Season season: show.getSeasons()){
            	Collections.sort(season.getEpisodes());
            }
            
        }
      
        return ok(views.html.index.render(unWatched, watching));
    }

    @Transactional
    public  static Result show() {
        return redirect(routes.Application.index());
    }

    @Transactional
    public static Result watch(long id) {
        TVShow tvShow = dao.findByEntityId(TVShow.class, id);
        tvShow.setFollowing(true);
        dao.merge(tvShow);
        dao.flush();
        return redirect(routes.Application.index());
    }

    @Transactional
    public static Result watchEpisode(long id) {
        Episode episode = dao.findByEntityId(Episode.class, id);
        episode.setWatched(true);
        dao.merge(episode);
        dao.flush();
        return redirect(routes.Application.index());
    }
    
    @Transactional
    public static Result setEpisodeGenerator(long id) {
    	Form<TVShow> form = shows.bindFromRequest();
    	TVShow show = dao.findByEntityId(TVShow.class, id);
    	String generatorValue = form.field("select-generator").value();
    	
    	switch(generatorValue){
    	case "older":
    		show.setEpisodeCalculator(new OlderEpisodeCalculator());
    		break;
    	case "newer":
    		show.setEpisodeCalculator(new NewerEpisodeCalculator());
    		break;
    	case "past-three":
    		show.setEpisodeCalculator(new PastThreeEpisodeCalculator());
    		break;
    	}
    	
    	dao.merge(show);
    	dao.flush();
    	
    	return redirect(routes.Application.index());
    }

}

package models.calculator;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;

import models.Episode;
import models.Season;
import models.TVShow;

/**
 * Created by: Julio Cesar Neves
 */

@Entity
public class OlderEpisodeCalculator extends NextEpisodeCalculator {
	
	@Override
	public Season getCurrentSeason(TVShow show){
		
		List<Season> seasons = (show == null) ? null : show.getSeasons();
		
		if(seasons == null) {
			return null;
		}
		
		Season seasonAux = null;
		Season currentSeason = null;
		Iterator<Season> it = seasons.iterator();
		
		while(it.hasNext()){
			seasonAux = it.next();
			if(seasonAux.isNotWatched() || seasonAux.isWatching()) {
				currentSeason = seasonAux;
				break;
			}
		}
		
		return currentSeason;
		
	}
	
	@Override
	public Episode getNextEpisode(TVShow show) {
		if(getCurrentSeason(show) == null){
			return null;
		}
		
		List<Episode> episodes = getCurrentSeason(show).getEpisodes();
		
		if(episodes.isEmpty()){
			return null;
		}
		
		Episode currentEpisode = null;
		Episode nextEpisode = null;
		Iterator<Episode> it = episodes.iterator();
		
		while(it.hasNext()){
			currentEpisode = it.next();
			if(!currentEpisode.isWatched()) {
				nextEpisode = currentEpisode;
				break;
			}
		}
		
		return nextEpisode;
	}
	
	@Override
	public String toString(){
		return "Antigos: O episódio não-assistido mais antigo será indicado como próximo";
	}
}

package models.calculator;

import java.util.List;

import javax.persistence.Entity;

import models.Episode;
import models.Season;
import models.TVShow;

/**
 * Created by: Julio Cesar Neves
 */

@Entity
public class NewerEpisodeCalculator extends NextEpisodeCalculator{
	
	@Override
	public Season getCurrentSeason(TVShow show){
		
		if(show == null){
			return null;
		}
		
		List<Season> seasons = (show == null) ? null : show.getSeasons();
		Season currentSeason = null;
		
		for(int i = seasons.size()-2; i >= 0; i--){
			if(seasons.get(i).isWatched()){
				if(!seasons.get(i+1).isWatched()){
					currentSeason = seasons.get(i+1);
				}
				break;
			}
		}
		
		//Special case for the first element of seasons list.
		if(currentSeason == null && !seasons.isEmpty() && !seasons.get(0).isWatched()){
			currentSeason =  seasons.get(0);
		}
		
		return currentSeason;
		
	}

	@Override
	public Episode getNextEpisode(TVShow show) {
		
		Season currentSeason = getCurrentSeason(show);
		
		if(currentSeason == null){
			return null;
		}
		
		List<Episode> episodes = currentSeason.getEpisodes();
		
		for(int i = episodes.size()-1; i >= 1; i--){
			if(!episodes.get(i).isWatched() &&
				episodes.get(i-1).isWatched()) {
				return episodes.get(i);
			} else if(episodes.get(i).isWatched()){
				return null;
			}
		}
		
		if(!episodes.isEmpty() && !episodes.get(0).isWatched()){
			return episodes.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public String toString(){
		return "Recentes: O episódio após o assistido mais recentemente será indicado como próximo";
	}
}

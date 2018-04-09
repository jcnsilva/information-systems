package models.calculator;

import javax.persistence.Entity;

import models.Episode;
import models.Season;
import models.TVShow;

@Entity
public class PastThreeEpisodeCalculator extends NextEpisodeCalculator {
	
	@Override
	public Season getCurrentSeason(TVShow show) {
		if(getNextEpisode(show) == null){
			return null;
		}else{
			return getNextEpisode(show).getSeason();
		}
	}

	@Override
	public Episode getNextEpisode(TVShow show) {
		Episode nextEpisode = null;
		int episodesWatchedAfter = 0;
		final int EPISODES_TH = 3; //threshold
		
		for(Season season: show.getSeasons()){
			for(Episode episode: season.getEpisodes()){
				
				//If nextEpisode is not set yet
				if(nextEpisode == null && !episode.isWatched()){
					nextEpisode = episode;
				} else if (	nextEpisode != null && episodesWatchedAfter >= EPISODES_TH &&
							!episode.isWatched()){
					nextEpisode = episode;
					episodesWatchedAfter = 0;
				} else if (nextEpisode != null && episode.isWatched()){
					episodesWatchedAfter++;	
				}
				
			}
		}
		
		//If the number of watched episodes is beyond the threshold after the iteration
		if(episodesWatchedAfter >= EPISODES_TH){
			nextEpisode = null;
		}
		
		return nextEpisode;
	}
	
	@Override
	public String toString(){
		return "Regra de 3: O epsisódio não-assistido mais antigo será indicado como próximo, "
				+ "a menos que você tenha assistido três ou mais eps após ele";
	}

}

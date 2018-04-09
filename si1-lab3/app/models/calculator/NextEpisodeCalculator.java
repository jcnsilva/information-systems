package models.calculator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import models.Episode;
import models.Season;
import models.TVShow;

/**
 * Created by: Julio Cesar Neves
 */

@Entity
@Table(name="NEXT_EPISODE_CALC")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class NextEpisodeCalculator {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	public abstract Season getCurrentSeason(TVShow show);
	
	public abstract Episode getNextEpisode(TVShow show);

}

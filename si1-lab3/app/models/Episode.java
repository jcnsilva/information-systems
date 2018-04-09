package models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by Leticia on 11/27/2014.
 */
@Entity(name = "Episode")
public class Episode implements Comparable<Episode>{

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private int number;
    
    @Column
    private boolean watched;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="SEASON_ID")
    private Season season;


    public Episode(String name, int number, Season season) {
        this.name = name;
        this.number = number;
        this.season = season;
        this.watched = false;
    }

    public Episode() {}

    public void setId(Long id) {
        this.id = id;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
        season.setStatus();
    }

    public Season getSeason() {
        return season;
    }

    
    public Episode getPreviousEpisode(){
    	int indexCurrentEpisode = season.getEpisodes().indexOf(this);
    	
    	if(indexCurrentEpisode <= 0){
    		return null;
    	} else {
    		return season.getEpisodes().get(indexCurrentEpisode-1);
    	}
    }
    
    public Episode getNextEpisode(){
    	int indexCurrentEpisode = season.getEpisodes().indexOf(this);
    	
    	if(indexCurrentEpisode == season.getEpisodes().size()-1){
    		return null;
    	} else {
    		return season.getEpisodes().get(indexCurrentEpisode+1);
    	}
    	
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + number;
		result = prime * result + ((season == null) ? 0 : season.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()){
			return false;
		}
		
		Episode other = (Episode) obj;
		if (name == null && other.name != null || 
			season == null & other.season != null) {
			return false;
		}
		
		return 	name.equals(other.name) &&
				season.equals(other.season) &&
				number == other.number;
	}
	
	@Override
	public String toString(){
		return 	season.toString() + ", episódio " +
				String.valueOf(number) + " - " + name;
	}

	@Override
	public int compareTo(Episode o) {
		return this.number - o.number;
	}

}

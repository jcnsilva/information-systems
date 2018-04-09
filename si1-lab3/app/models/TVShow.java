package models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import models.calculator.NewerEpisodeCalculator;
import models.calculator.NextEpisodeCalculator;

/**
 * Created by Leticia on 11/27/2014.
 */

@Entity(name = "TVShow")
public class TVShow implements Comparable<TVShow>{
	
	@Id
	@GeneratedValue
   	@SequenceGenerator(name = "TVSHOW_SEQUENCE", sequenceName = "TVSHOW_SEQUENCE", allocationSize = 1, initialValue = 0)
    private Long id;

    @Column
    private String name;

    @Column
    private boolean following;
    
    @OneToOne(cascade = CascadeType.ALL)
    private NextEpisodeCalculator neCalculator;
    
    
    @OneToMany(	cascade = CascadeType.ALL,
    			mappedBy = "tvShow")
    private List<Season> seasons;
    
    
    public TVShow(String name, NextEpisodeCalculator neCalculator) {
		setEpisodeCalculator(neCalculator);
		this.name = name;
        seasons = new LinkedList<Season>();
        following = false;
	}
    
    public TVShow(String name) {
        this(name, null);
    }
    

    public TVShow() {}

    
    public Long getId() {
        return id;
    }
	

    public void setId(Long id) {
        this.id = id;
    }
    

    public boolean isFollowing() {
        return following;
    }
    

    public void setFollowing(boolean following) {
        this.following = following;
    }
    

    public String getName() {
        return name;
    }
    

    public void setName(String name) {
        this.name = name;
    }
    

    public List<Season> getSeasons() {
        return seasons;
    }
    

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }
    
    
    public void addSeason(Season season) {
        seasons.add(season);
    }
    
    
    public final void setEpisodeCalculator(NextEpisodeCalculator NECalculator){
    	if(NECalculator == null) {
    		this.neCalculator = new NewerEpisodeCalculator(); // default calculator
    	} else {
    		this.neCalculator = NECalculator;
    	}
    }
    
    public String calculatorAsString(){
    	return neCalculator.toString();
    }


	public Episode getNextEpisode() {
		return neCalculator.getNextEpisode(this);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		
		if (obj == null || this.getClass() != obj.getClass()){
			return false;
		}
		
		TVShow other = (TVShow) obj;
		if (name == null && other.name != null){
			return false;
		}
		
		return 	name.equals(other.name);
	}
	
	
	@Override
	public String toString(){
		return name;
	}

	@Override
	public int compareTo(TVShow other) {
		return this.getName().compareTo(other.getName());
	}
}

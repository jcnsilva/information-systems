package models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Created by Leticia on 11/27/2014.
 */
@Entity(name = "Season")
public class Season implements Comparable<Season>{
	
	public enum Status {
		WATCHING, WATCHED, TO_WATCH;
	}
	
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int number;
    

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
    

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="TV_SHOW_ID")
    private TVShow tvShow;
    
    
    @OneToMany(	cascade = CascadeType.ALL,
    			mappedBy = "season")
    private List<Episode> episodes;
    

    public Season(int number, TVShow tvShow) {
        this.number = number;
        this.tvShow = tvShow;
        episodes = new LinkedList<Episode>();
        status = Status.TO_WATCH;
    }
    

    public Season() {
    }
    

    public List<Episode> getEpisodes() {
        return episodes;
    }
    

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
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
    

    public void setId(Long id) {
        this.id = id;
    }
    

    public TVShow getTvShow() {
        return tvShow;
    }
    

    public void setTvShow(TVShow tvShow) {
        this.tvShow = tvShow;
    }
    

    public void addEpisode(Episode episode) {
        episodes.add(episode);
    }
    
    public void setStatus() {
        boolean areAllWatched = true;
        boolean isOneWatched = false;
        for(int i = 0; i < episodes.size(); i++) {
            if(episodes.get(i).isWatched()) {
                isOneWatched = true;
            }
            if(!episodes.get(i).isWatched()) {
                areAllWatched = false;
            }
        }
        
        if(areAllWatched && episodes.size() != 0) {
            status = Status.WATCHED;
        } else if(isOneWatched){
            status = Status.WATCHING;
        } else {
            status = Status.TO_WATCH;
        }
    }
    

    public boolean isWatched(){
    	setStatus();
    	return status == Status.WATCHED;
    }
    
    
    public boolean isWatching(){
    	setStatus();
    	return status == Status.WATCHING;
    }
    
    
    public boolean isNotWatched(){
    	setStatus();
    	return status == Status.TO_WATCH;
    }
    

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		result = prime * result + ((tvShow == null) ? 0 : tvShow.hashCode());
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
		
		Season other = (Season) obj;
		if (tvShow == null && other.tvShow != null){
			return false;
		}
		
		return 	number == other.number &&
				tvShow.equals(other.tvShow);		
	}
	
	@Override
	public String toString(){
		return tvShow.toString() + ", temporada " + String.valueOf(number);
	}


	@Override
	public int compareTo(Season o) {
		return this.number - o.number;
	}
    
    
}

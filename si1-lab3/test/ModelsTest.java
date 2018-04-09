import static org.fest.assertions.Assertions.assertThat;
import models.Episode;
import models.Season;
import models.TVShow;
import models.calculator.NewerEpisodeCalculator;
import models.calculator.OlderEpisodeCalculator;
import models.calculator.PastThreeEpisodeCalculator;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Leticia on 12/5/2014.
 */
public class ModelsTest {
    TVShow show;
    Season season1;
    Episode episode1;
    Episode episode2;
    Season season2;
    OlderEpisodeCalculator oepCalculator;

    @Before
    public void setUp() {
    	oepCalculator = new OlderEpisodeCalculator();
        show = new TVShow("Show", oepCalculator);
        season1 = new Season(1, show);
        episode1 = new Episode("Episode1",1,season1);
        episode2 = new Episode("Episode2",2,season1);
        season2 = new Season(2, show);
        season1.addEpisode(episode1);
        season1.addEpisode(episode2);
        show.addSeason(season1);
        show.addSeason(season2);
    }

    @Test
    public void shouldWatchEpisode() {
        assertThat(season1.isNotWatched()).isTrue();
        episode1.setWatched(true);
        assertThat(episode1.isWatched()).isTrue();
    }

    @Test
    public void shouldWatchEntireSeason(){
        assertThat(season1.isNotWatched()).isTrue();
        
        episode1.setWatched(true);
        assertThat(season1.isWatching()).isTrue();
        
        episode2.setWatched(true);
        assertThat(season1.isWatched()).isTrue();
    }
    
    @Test
    public void emptySeasonsShouldBeNotWatched(){
    	assertThat(season2.isNotWatched()).isTrue();
    } 

    @Test
    public void shouldCalculateNextEpisodeCorrectly() {
    	//Using OlderCalculator
    	show.setEpisodeCalculator(new OlderEpisodeCalculator());
        assertThat(show.getNextEpisode()).isEqualTo(episode1);
        
        episode2.setWatched(true);
        assertThat(show.getNextEpisode()).isEqualTo(episode1);
        
        episode1.setWatched(true);
        assertThat(show.getNextEpisode()).isNull();
        
        //Using NewerCalculator
        episode1.setWatched(false);
        episode2.setWatched(false);
        show.setEpisodeCalculator(new NewerEpisodeCalculator());
        
        assertThat(show.getNextEpisode()).isEqualTo(episode1);
        
        episode2.setWatched(true);
        assertThat(show.getNextEpisode()).isNull();
        
        episode2.setWatched(false);
        assertThat(show.getNextEpisode()).isEqualTo(episode1);
        
        episode1.setWatched(true);
        assertThat(show.getNextEpisode()).isEqualTo(episode2);
        
        
        //Using PastThreeCalculator
        show.setEpisodeCalculator(new PastThreeEpisodeCalculator());
        episode1.setWatched(false);
        episode2.setWatched(false);
        Episode episode3 = new Episode("Episode3",3,season1);
        Episode episode4 = new Episode("Episode4",4,season1);
        Episode episode5 = new Episode("Episode5",5,season1);
        season1.addEpisode(episode3);
        season1.addEpisode(episode4);
        season1.addEpisode(episode5);
        
        assertThat(show.getNextEpisode()).isEqualTo(episode1);
        
        episode2.setWatched(true);
        assertThat(show.getNextEpisode()).isEqualTo(episode1);
        
        episode3.setWatched(true);
        assertThat(show.getNextEpisode()).isEqualTo(episode1);
        
        episode4.setWatched(true);
        assertThat(show.getNextEpisode()).isEqualTo(episode5);
        
        episode5.setWatched(true);
        assertThat(show.getNextEpisode()).isNull();
    }

}

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import models.Episode;
import models.Season;
import models.TVShow;

import org.junit.Test;

import base.AbstractTest;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest extends AbstractTest{

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    @Test
    public void shouldStartDatabaseWithManySeries() {
        List<TVShow> tvShows = dao.findAllByClassName(TVShow.class.getName());
        assertThat(tvShows.size()).isNotEqualTo(0);
    }


    @Test
    public void shouldUpdateSeriesToFollowingInDatabase() {
        TVShow show = new TVShow("Show");
        dao.persist(show);

        TVShow show1 = dao.findByEntityId(TVShow.class, show.getId());
        assertThat(show1.isFollowing()).isEqualTo(false);
        show1.setFollowing(true);
        dao.merge(show1);

        show1 = dao.findByEntityId(TVShow.class, show.getId());
        assertThat(show1.isFollowing()).isEqualTo(true);
    }

    @Test
    public void shouldUpdateEpisodeToWatchedInDatabase() {
        TVShow show = new TVShow("Show");
        Season season = new Season(1, show);
        Episode episode = new Episode("Episode",1,season);
        season.addEpisode(episode);
        show.addSeason(season);
        dao.persist(show);

        Episode episode1 = dao.findByEntityId(Episode.class, episode.getId());
        assertThat(episode1.isWatched()).isEqualTo(false);
        episode1.setWatched(true);
        dao.merge(episode1);

        episode1 = dao.findByEntityId(Episode.class, episode.getId());
        assertThat(episode1.isWatched()).isEqualTo(true);
    }

    @Test
    public void shouldUpdateSeasonStatusToWatchingInDatabase() {
        TVShow show = new TVShow("Show");
        Season season = new Season(1, show);
        Episode episode = new Episode("Episode",1,season);
        Episode episode0 = new Episode("Episode0",1,season);
        season.addEpisode(episode);
        season.addEpisode(episode0);
        show.addSeason(season);
        dao.persist(show);

        Episode episode1 = dao.findByEntityId(Episode.class, episode.getId());
        assertThat(episode1.getSeason().isNotWatched()).isTrue();
        episode1.setWatched(true);
        dao.merge(episode1);

        episode1 = dao.findByEntityId(Episode.class, episode.getId());
        assertThat(episode1.getSeason().isWatching()).isTrue();
    }

    @Test
    public void shouldUpdateSeasonStatusToWatchedInDatabase() {
        TVShow show = new TVShow("Show");
        Season season = new Season(1, show);
        Episode episode = new Episode("Episode",1,season);
        season.addEpisode(episode);
        show.addSeason(season);
        dao.persist(show);

        Episode episode1 = dao.findByEntityId(Episode.class, episode.getId());
        assertThat(episode1.getSeason().isNotWatched()).isTrue();
        episode1.setWatched(true);
        dao.merge(episode1);

        episode1 = dao.findByEntityId(Episode.class, episode.getId());
        assertThat(episode1.getSeason().isWatched()).isTrue();
    }
}

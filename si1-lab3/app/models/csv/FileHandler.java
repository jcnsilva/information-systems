package models.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import models.Episode;
import models.Season;
import models.TVShow;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.Play;

/**
 * Created by Leticia on 12/1/2014.
 */
public class FileHandler {

    public static void read() {
        URL csvFile = Play.application().resource("public/seriesFinalFileMini.csv");
        BufferedReader reader = null;
        String splitBy = ",";
        try {
            reader = new BufferedReader(new InputStreamReader(csvFile.openStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            populateDatabase(reader, splitBy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void populateDatabase(BufferedReader reader, String splitBy) throws IOException {
        GenericDAO dao = new GenericDAOImpl();
        
        if(dao.findAllByClassName("TVShow").isEmpty()){
	        
	        String line = reader.readLine();
	        String[] args = line.split(splitBy);
	        String tvShowName = args[0];
	        int seasonNumber = Integer.parseInt(args[1]);
	        int episodeNumber = Integer.parseInt(args[2]);
	        String episodeName = args[3];
	        TVShow show = new TVShow(tvShowName);
	        Season season = new Season(seasonNumber, show);
	        Episode episode = new Episode(episodeName,episodeNumber,season);
	        season.addEpisode(episode);
	        show.addSeason(season);
	
	        while ((line = reader.readLine()) != null) {
	            args = line.split(splitBy);
	            tvShowName = args[0];
	            seasonNumber = Integer.parseInt(args[1]);
	            episodeNumber = Integer.parseInt(args[2]);
	            if (args.length > 3) {
	                episodeName = args[3];
	            } else {
	                episodeName = "";
	            }
	
	            if(tvShowName.equals(show.getName())) {
	                if(seasonNumber == season.getNumber()) {
	                    episode = new Episode(episodeName,episodeNumber,season);
	                    season.addEpisode(episode);
	                } else {
	                    season = new Season(seasonNumber, show);
	                    episode = new Episode(episodeName,episodeNumber,season);
	                    season.addEpisode(episode);
	                    show.addSeason(season);
	                }
	            } else {
	                dao.persist(show);
	                dao.flush();
	                show = new TVShow(tvShowName);
	                season = new Season(seasonNumber, show);
	                episode = new Episode(episodeName,episodeNumber,season);
	                season.addEpisode(episode);
	                show.addSeason(season);
	            }
	        }
        }
    }

}

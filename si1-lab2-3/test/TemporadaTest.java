import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.assertThat;
import models.Episodio;
import models.Serie;
import models.Temporada;

import org.junit.Before;
import org.junit.Test;

import play.Logger;


public class TemporadaTest {
	
	private Episodio episodio1, episodio2, episodio3, episodio4, episodio5;
	private Temporada temp1, temp2, temp3;
	private Serie serie1;
	
	@Before
	public void setUp() throws Exception{
		serie1 = new Serie("Serie");
		
		temp1 = new Temporada(1, serie1);
		temp2 = new Temporada(2, serie1);
		temp3 = new Temporada(3, serie1);
		
		episodio1 = new Episodio("s1e1", 1, temp1);
		episodio2 = new Episodio("s1e2", 2, temp1);
		episodio3 = new Episodio("s1e3", 3, temp1);
		episodio4 = new Episodio("s2e1", 1, temp2);
		episodio5 = new Episodio("s3e1", 1, temp3);
				
		
		temp1.addEpisodio(episodio1);
		temp1.addEpisodio(episodio2);
		temp1.addEpisodio(episodio3);
		temp2.addEpisodio(episodio4);
		temp3.addEpisodio(episodio5);
		
		serie1.addTemporada(temp1);
		serie1.addTemporada(temp2);
		serie1.addTemporada(temp3);
	}
	
	@Test
	public void testaProximoEpisodio() throws Exception {
		assertThat(temp1.getProximoEpisodio()).isEqualTo(episodio1);
		
		episodio1.setAssistido(true);
		assertThat(temp1.getProximoEpisodio()).isEqualTo(episodio2);
		
		episodio2.setAssistido(true);
		assertThat(temp1.getProximoEpisodio()).isEqualTo(episodio3);
		
		episodio3.setAssistido(true);
		assertThat(temp1.getProximoEpisodio()).isEqualTo(episodio3);
		
		//Serie com uma temporada de um episodio
		Serie serie2 = new Serie("Série 2");
		Temporada temp4 = new Temporada(1, serie2);
		Episodio ep1 = new Episodio("Piloto", 1, temp4);
		temp4.addEpisodio(ep1);
		serie2.addTemporada(temp4);
		
		assertThat(temp4.getProximoEpisodio()).isEqualTo(ep1);
		
		ep1.setAssistido(true);
		assertThat(temp4.getProximoEpisodio()).isEqualTo(ep1);
	}
	
	@Test
	public void testaUpdateStatus() throws Exception {
		assertThat(temp1.isNaoAssistida()).isTrue();
		
		episodio1.setAssistido(true);
		assertThat(temp1.isAssistindo()).isTrue();
		
		episodio2.setAssistido(true);
		episodio3.setAssistido(true);
		assertThat(temp1.isAssistida()).isTrue();
		
		//Serie com uma temporada de um episodio
		Serie serie2 = new Serie("Série 2");
		Temporada temp4 = new Temporada(1, serie2);
		Episodio ep1 = new Episodio("Piloto", 1, temp4);
		temp4.addEpisodio(ep1);
		serie2.addTemporada(temp4);
		
		assertThat(temp4.isNaoAssistida()).isTrue();
		
		ep1.setAssistido(true);
		assertThat(temp4.isAssistida()).isTrue();
	}

}

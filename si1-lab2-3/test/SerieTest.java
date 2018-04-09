import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.assertThat;
import models.Episodio;
import models.Serie;
import models.Temporada;

import org.junit.Before;
import org.junit.Test;


public class SerieTest {
	
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
	public void testTemporadaAtual() throws Exception{
		assertThat(serie1.getTemporadaAtual()).isEqualTo(temp1);
		
		episodio1.setAssistido(true);
		episodio2.setAssistido(true);
		assertThat(serie1.getTemporadaAtual()).isEqualTo(temp1);
		
		episodio3.setAssistido(true);
		assertThat(serie1.getTemporadaAtual()).isEqualTo(temp2);
		
		episodio4.setAssistido(true);
		assertThat(serie1.getTemporadaAtual()).isEqualTo(temp3);
		
		episodio5.setAssistido(true);
		assertThat(serie1.getTemporadaAtual()).isEqualTo(temp3);	
		
		//Serie com uma temporada de um episodio
		Serie serie2 = new Serie("Série 2");
		Temporada temp4 = new Temporada(1, serie2);
		Episodio ep1 = new Episodio("Piloto", 1, temp4);
		temp4.addEpisodio(ep1);
		serie2.addTemporada(temp4);
		
		assertThat(serie2.getTemporadaAtual()).isEqualTo(temp4);
		
		ep1.setAssistido(true);
		assertThat(serie2.getTemporadaAtual()).isEqualTo(temp4);
	}
	
	@Test
	public void testUpdateStatus(){
		assertThat(serie1.isNaoAssistida()).isTrue();
		
		episodio1.setAssistido(true);
		episodio2.setAssistido(true);
		assertThat(serie1.isAssistindo()).isTrue();
		
		episodio3.setAssistido(true);
		episodio4.setAssistido(true);
		episodio5.setAssistido(true);
		assertThat(serie1.isAssistida()).isTrue();
		
	}

}

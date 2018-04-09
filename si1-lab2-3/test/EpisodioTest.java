import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.assertThat;
import models.Episodio;
import models.Serie;
import models.Temporada;

import org.junit.Before;
import org.junit.Test;

import play.Logger;

public class EpisodioTest {
	private Episodio ep1, ep2, ep3;
	private Temporada temp1, temp2;
	private Serie serie1;
	
	@Before
	public void setUp() throws Exception {
		serie1 = new Serie("Série");
		temp1 = new Temporada(1, serie1);
		temp2 = new Temporada(2, serie1);
		ep1 = new Episodio("Piloto", 1, temp1);
		ep2 = new Episodio("Piloto", 1, temp1);
		ep3 = new Episodio("Piloto", 1, temp2);
		
		serie1.addTemporada(temp1);
		serie1.addTemporada(temp2);
		temp1.addEpisodio(ep1);
		temp1.addEpisodio(ep2);
		temp2.addEpisodio(ep3);
	}
	
	@Test
	public void testaEpisodioAssistido() throws Exception {
		ep1.setAssistido(true);
		assertThat(ep1.isAssistido()).isTrue();
		
		ep1.setAssistido(false);
		assertThat(ep1.isAssistido()).isFalse();
	}
}

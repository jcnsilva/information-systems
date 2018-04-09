import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import play.*;
import play.db.jpa.JPA;

import models.DAO.DAO;
import models.Serie;
import models.Temporada;
import models.Episodio;

public class Global extends GlobalSettings {
	
	private static DAO dao = new DAO();
	
	@Override
    public void onStart(Application app) {
        Logger.info("Aplicação inicializada...");

        JPA.withTransaction(new play.libs.F.Callback0() {
        	
        	
        	
            @Override
            public void invoke() throws Throwable {
            	//Armazena dados do csv numa lista de arrays
            	String arquivo = "seriesFinalFile.csv"; 
            	String separador = ",";
            	String entrada = "";
            	List<String[]> entradas = new ArrayList<String[]>();
            	
            	
            	try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))){        
            		
            		while((entrada = reader.readLine()) != null){
            			
            			String[] entradaArray = entrada.split(separador);
            			entradas.add(entradaArray);
            			
            		}
            		
            	} catch (IOException e){
            		e.printStackTrace();
            	}
            	
            	List<Serie> novasSeries = new ArrayList<Serie>();
            	
            	//Cria Series, temporadas e episódios
            	Iterator<String[]> it = entradas.iterator();
            	while(it.hasNext()){
            		String[] entradaAtual = it.next();
            		
            		String nomeSerie = entradaAtual[0];
            		String numTemporada = entradaAtual[1];
            		String numEpisodioTemp = entradaAtual[2];
            		String nomeEpisodio = (entradaAtual.length >= 4) ? entradaAtual[3] : "";
            		
            		
            		//Se a série não existe, cria uma série...
            		Serie serieAtual = new Serie(nomeSerie);
            		if (!novasSeries.contains(serieAtual)) {
            			
            			 Temporada novaTemporada = new Temporada(Integer.parseInt(numTemporada), serieAtual);
            			 Episodio novoEpisodio = new Episodio(nomeEpisodio, Integer.parseInt(numEpisodioTemp), novaTemporada);
            			       
            			 novaTemporada.addEpisodio(novoEpisodio);
            			 serieAtual.addTemporada(novaTemporada);
            			 novasSeries.add(serieAtual);             			           			 
            		
            		//...Caso contrário, adiciona o episodio a uma série ja existente
            		} else {
            			 
            			Serie serieProcurada = novasSeries.get(novasSeries.indexOf(serieAtual));
            			List<Temporada> tempProcuradas = serieProcurada.getTemporadas();
            			
            			//Se a temporada não existe, cria uma temporada...
            			Temporada temporadaAtual = new Temporada(Integer.parseInt(numTemporada), serieProcurada);
            			if(!tempProcuradas.contains(temporadaAtual)){
            				Episodio novoEpisodio = new Episodio(nomeEpisodio, Integer.parseInt(numEpisodioTemp), temporadaAtual);
            				serieProcurada.addTemporada(temporadaAtual);
            				temporadaAtual.addEpisodio(novoEpisodio);
            			
            			//...Caso contrário, adiciona o episodio a uma temporada existente
            			} else {
            				Temporada temporadaProcurada = tempProcuradas.get(tempProcuradas.indexOf(temporadaAtual));
            				Episodio novoEpisodio = new Episodio(nomeEpisodio, Integer.parseInt(numEpisodioTemp), temporadaProcurada);
            				temporadaProcurada.addEpisodio(novoEpisodio);
            			}
            		}
            		
            	}
            	
            	Logger.info("Adicionando séries. Essa operação pode levar cerca de um minuto");
            	//Adiciona todas as séries no BD
            	for(Serie serie: novasSeries){
            		Logger.info("Adicionando série " + serie.getNome());
            		dao.persist(serie);
            		dao.flush();
            	}
            }
        });
    }
	
	@Override
    public void onStop(Application app) {
        Logger.info("Aplicação finalizada...");

        JPA.withTransaction(new play.libs.F.Callback0() {
        	
            @Override
            public void invoke() throws Throwable {
            	List<Serie> series = dao.findAllByClass(Serie.class);
            	for(Serie serie: series){
            		Logger.info("Removendo série " + serie.getNome());
            		dao.remove(serie);
            		dao.flush();            		
            	}
            }
        });
    }
}

package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.Logger;

@Entity
public class Temporada {
	
	public enum Status{NAO_ASSISTIDA, ASSISTINDO, ASSISTIDA}
	
	@OneToMany(	cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
				fetch=FetchType.LAZY,
				mappedBy="temporada")	
	private List<Episodio> episodios;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column
	private int numTemporada;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="serie_id")
	private Serie serie;
	
	public Temporada(){}
	
	public Temporada(int numTemporada, Serie serie) throws Exception{
		
		this.episodios = new ArrayList<>();
		setNumTemporada(numTemporada);
		setSerie(serie);
		this.status = Status.NAO_ASSISTIDA;
	}
	
	public void addEpisodio(Episodio novoEpisodio) throws Exception{
		if(novoEpisodio == null)
			throw new Exception("Episódio não pode ser null");
		
		episodios.add(novoEpisodio);
	}
	
	public Long getId(){
		return id;
	}
	
	public boolean isAssistida(){
		return status == Status.ASSISTIDA;
	}
	
	public boolean isAssistindo(){
		return status == Status.ASSISTINDO;
	}
	
	public boolean isNaoAssistida(){
		return status == Status.NAO_ASSISTIDA;
	}

	public void updateStatus() {
		Episodio proxEpisodio = getProximoEpisodio();
		
		if (proxEpisodio.equals(episodios.get(0)) && !proxEpisodio.isAssistido())
			this.status = Status.NAO_ASSISTIDA;
		else if (proxEpisodio.equals(episodios.get(episodios.size() - 1)) && proxEpisodio.isAssistido())
			this.status = Status.ASSISTIDA;
		else
			this.status = Status.ASSISTINDO;
		
		this.serie.updateStatus();
		
	}

	public List<Episodio> getEpisodios() {
		return episodios;
	}
	
	public void setSerie(Serie serie) throws Exception{
		if(serie == null)
			throw new Exception("Serie não pode ser vazia");
		this.serie = serie;
	}
	
	public Serie getSerie(){
		return serie;
	}
	
	public void setNumTemporada(int numTemporada) throws Exception {
		if(numTemporada < 0)
			throw new Exception("O número da temporada deve ser positivo");
		this.numTemporada = numTemporada;
	}

	public int getNumTemporada(){
		return numTemporada;
	}
	
	public Episodio getProximoEpisodio(){
		Episodio epiAtual = null;
		Episodio proxEpisodio = null;
		Iterator<Episodio> it = episodios.iterator();
		
		while(it.hasNext()){
			epiAtual = it.next();
			if(!epiAtual.isAssistido()){
				proxEpisodio = epiAtual;
				break;
			}
		}
		
		//Se todos os episodios da temporada já foram assistidos,
		//o proximo Episodio será o último da temporada
		if(proxEpisodio == null)
			proxEpisodio = epiAtual;
		
		return proxEpisodio;
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numTemporada;
		result = prime * result + ((serie == null) ? 0 : serie.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		Temporada other = (Temporada) obj;
		if (numTemporada != other.numTemporada)
			return false;
		
		if (serie == null && other.serie != null)
			return false;
		else 
			return serie.equals(other.serie);
	}

	@Override
	public String toString(){
		String result = "";
		result += "Temporada " + this.getNumTemporada() + "\n";
		
		for(Episodio epi: episodios){
			result += epi.toString() + "\n";
		}
		
		return result;
	}


}

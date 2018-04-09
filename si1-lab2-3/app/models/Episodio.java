package models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Episodio {
	
	private boolean assistido;
	private String nome;
	private int numEpisodioTemp;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="temporada_id")
	private Temporada temporada;

	public Episodio(){}
	
	public Episodio(String nome, int numEpisodioTemp, Temporada temporada) throws Exception{
		this.setNome(nome);
		this.setNumEpisodioTemp(numEpisodioTemp);
		this.setTemporada(temporada);
		this.assistido = false;
	}
	
	public Long getId(){
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isAssistido() {
		return assistido;
	}

	public void setAssistido(boolean status) {
		this.assistido = status;
		this.temporada.updateStatus();
	}

	public int getNumEpisodioTemp() {
		return numEpisodioTemp;
	}

	public void setNumEpisodioTemp(int numEpisodioTemp) throws Exception {
		if(numEpisodioTemp < 0)
			throw new Exception("O número do episódio deve ser positivo");
		this.numEpisodioTemp = numEpisodioTemp;
	}
	
	public void setTemporada(Temporada temporada) throws Exception{
		if(temporada == null)
			throw new Exception("Temporada não pode ser nula");
		this.temporada = temporada;
	}
	
	public Temporada getTemporada(){
		return temporada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numEpisodioTemp;
		result = prime * result
				+ ((temporada == null) ? 0 : temporada.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		Episodio other = (Episodio) obj;
		if (numEpisodioTemp != other.numEpisodioTemp)
			return false;
		
		if (temporada == null && other.temporada != null)
			return false;
		else 
			return temporada.equals(other.temporada);
	}

	@Override
	public String toString(){
		return "Episodio " + this.getNumEpisodioTemp() + " - " + this.getNome();
	}

}

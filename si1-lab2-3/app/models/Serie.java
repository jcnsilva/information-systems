package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Serie {
	
	public enum Status{ NAO_ASSISTIDA, ASSISTINDO, ASSISTIDA }
	
	@OneToMany(	cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
				fetch=FetchType.LAZY,
				mappedBy="serie")
	private List<Temporada> temporadas;
	
	private String nome;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Id
	@GeneratedValue
	private Long id;
	
	public Serie(){}
	
	public Serie(String nome) {
		temporadas = new ArrayList<Temporada>();
		setNome(nome);
		this.status = Status.NAO_ASSISTIDA;
	}
	
	public void addTemporada(Temporada novaTemporada) throws Exception{
		if(novaTemporada == null)
			throw new Exception("Nova temporada não pode ser null");
		
		temporadas.add(novaTemporada);
	}
	
	public void setNome(String nome){
		this.nome = nome;
	}
	
	public String getNome(){
		return this.nome;
	}

	public Long getId() {
		return this.id;
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
		Temporada temporadaAtual = getTemporadaAtual();
		
		if(temporadaAtual.equals(temporadas.get(temporadas.size()-1)) && temporadaAtual.isAssistida())
			this.status = Status.ASSISTIDA;
		else if(temporadaAtual.equals(temporadas.get(0)) && temporadaAtual.isNaoAssistida())
			this.status = Status.NAO_ASSISTIDA;
		else
			this.status = Status.ASSISTINDO;
		
	}
	
	public void setAssistindo(){
		this.status = Status.ASSISTINDO;
	}
	
	public List<Temporada> getTemporadas(){
		return temporadas;
	}
	
	public Temporada getTemporadaAtual(){
		Temporada temporadaAtual = null;
		Temporada temporadaAux = null;
		Iterator<Temporada> it = temporadas.iterator();
		
		while(it.hasNext()){
			temporadaAux = it.next();
			if(temporadaAux.isAssistindo() || temporadaAux.isNaoAssistida()){
				temporadaAtual = temporadaAux;
				break;
			}
		}
		
		//Se todas as temporadas já foram assistidas,
		//a temporada atual será a última da série
		if(temporadaAtual == null)
			temporadaAtual = temporadaAux;
		
		return temporadaAtual;
	}
	
	public Episodio getProximoEpisodio(){
		return getTemporadaAtual().getProximoEpisodio();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		Serie other = (Serie) obj;
		if (nome == null && other.nome != null)
			return false;
		else
			return nome.equals(other.nome);
	}
	
	@Override
	public String toString(){
		String result = "";
		result += this.getNome() + "\n";
		
		for(Temporada temp: temporadas){
			result += temp.toString() + "\n";
		}
		
		return result;
	}
	

}

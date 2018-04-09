package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

/**
 * Uma classe que representa um tema estudado em uma disciplina
 * @author Júlio Neves
 *
 */
@Entity
public class Tema {
	
	public enum Dificuldade{
		MUITO_FACIL(-2), FACIL(-1), MEDIO(0), DIFICIL(1), MUITO_DIFICIL(2);
		
		private int valorNumerico;
		private Dificuldade(int valor){
			valorNumerico = valor;
		}
		
		public int getValorNumerico(){
			return valorNumerico;
		}
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String nome;
	
	@ElementCollection
	@CollectionTable(name="AVAL_ALUNO")
    @MapKeyJoinColumn(name="ALUNO_ID")
    @Column(name="AVALIACOES")
	private Map<Aluno, Dificuldade> avaliacoes;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Dica> dicas;
	
	
	public Tema(String nome){
		dicas = new ArrayList<Dica>();
		avaliacoes = new HashMap<Aluno, Dificuldade>();
		this.nome = nome;
	}
	
	public Tema(){
		this("");
	}

	public String getNome(){
		return nome;
	}
	
	public Long getId(){
		return id;
	}
	
	public void addAvaliacao(Aluno aluno, Dificuldade dif){
		avaliacoes.put(aluno, dif);
	}
	
	public double getAvaliacaoMedia(){
		if(avaliacoes.isEmpty())
			return 0;
		else
			return calculaSomaAvaliacoes() / avaliacoes.size();
	}
	
	public double getAvaliacaoMediana(){
		Dificuldade[] avals = avaliacoes.values().toArray(new Dificuldade[0]);
		double mediana;
		
		if (avaliacoes.isEmpty()){
			mediana = 0;
		} else if(avaliacoes.size() % 2 != 0){
			mediana = avals[avals.length / 2].getValorNumerico();
		} else {
			double soma = 	avals[(avals.length / 2) - 1].getValorNumerico() +
							avals[(avals.length / 2)].getValorNumerico();
			mediana = soma / 2;
		}
		
		return mediana;
	}
	
	private double calculaSomaAvaliacoes(){
		Iterator<Aluno> it = avaliacoes.keySet().iterator();
		double soma = 0;
		
		while(it.hasNext()){
			soma += avaliacoes.get(it.next()).getValorNumerico();
		}
		
		return soma;
	}

	public int totalAvaliacoes() {
		return avaliacoes.size();
	}

	public int totalDicas() {
		return dicas.size();
	}

	public void addDica(Dica dica) {
		dicas.add(dica);
		
	}
	
	public List<Dica> getDicas(){
		return dicas;
	}
	
}

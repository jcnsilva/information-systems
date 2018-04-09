package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="DICA")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Dica implements Comparable<Dica>{
	
	public enum Status{ ABERTA, FECHADA; }
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@ElementCollection
	@CollectionTable(name="OPINIAO_ALUNO")
    @MapKeyJoinColumn(name="ALUNO_ID")
    @Column(name="OPINIOES")
	private Map<Aluno, Opiniao> opinioes;
	
	@OneToMany(cascade=CascadeType.ALL)
	private Set<Aluno> reportadores;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public Dica(){
		this.opinioes = new HashMap<Aluno, Opiniao>();
		this.reportadores = new HashSet<Aluno>();
		this.status = Status.ABERTA;
	}
	
	public final Long getId(){
		return id;
	}
	
	public final void addOpiniao(Aluno aluno, Opiniao opiniao){
		opinioes.put(aluno, opiniao);
		setStatus();
	}
	
	public final int getTotalConcordancias(){
		int concordancia = 0;
		Iterator<Aluno> it = opinioes.keySet().iterator();
		
		while(it.hasNext()){
			if(opinioes.get(it.next()).getConcordancia() == true)
				concordancia++;
		}		
		
		return concordancia;
	}
	
	public final int getTotalDiscordancias(){
		return getTotalOpinioes() - getTotalConcordancias();
	}
	
	public final int getTotalOpinioes(){
		return opinioes.size();
	}
	
	public final double getTaxaDeConcordancia(){
		if(getTotalOpinioes() == 0)
			return 0;
		else
			return getTotalConcordancias() * 1.0 / getTotalOpinioes();
	}
	
	public final List<String> getTextosDiscordancia(){
		ArrayList<String> textos = new ArrayList<>();
		Iterator<Aluno> it = opinioes.keySet().iterator();
		
		while(it.hasNext()){
			Aluno alunoAtual = it.next();
			if(opinioes.get(alunoAtual).getConcordancia() == false)
				textos.add(alunoAtual.getUsername() + " discordou: " +
						opinioes.get(alunoAtual).getTextoDiscordancia()
				);
		}
		
		return textos;
	}
	
	public final int getTotalReports(){
		return reportadores.size();
	}
	
	public final boolean addReportador(Aluno aluno){
		if(aluno != null){
			reportadores.add(aluno);
			setStatus();
			return true;
		}
		return false;
	}
	
	public final boolean isMetadica(){
		return this instanceof MetaDica;
	}
	
	public final int compareTo(Dica outraDica){
		if(outraDica == null)
			return 1;
		
		if(this.getTotalConcordancias() > outraDica.getTotalConcordancias())
			return 1;
		else if(this.getTotalConcordancias() < outraDica.getTotalConcordancias())
			return -1;
		else
			return 0;
	}
	
	public abstract String toString();
	
	private final void setStatus(){
		if(getTotalReports() >= 3 || getTotalOpinioes() >= 20)
			this.status = Status.FECHADA;
		else
			this.status = Status.ABERTA;
	}
}

package models;

import javax.persistence.*;

@Entity
public class DicaDisciplina extends Dica{
	
	private String nome;
	private String razao;
	
	@OneToOne
	private Aluno aluno;
	
	public DicaDisciplina(Aluno aluno, String nome, String razao){
		this.aluno = aluno;
		this.nome = nome;
		this.razao = razao;
	}
	
	public DicaDisciplina(){
		this(null, "", "");
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	@Override
	public String toString() {
		return "Dica de disciplina: " + getNome() + 
				"\nRazão: " + getRazao();
	}

}

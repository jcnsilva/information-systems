package models;

import javax.persistence.*;

@Entity
public class DicaAssunto extends Dica{
	
	private String assunto;
	
	@OneToOne
	private Aluno aluno;
	
	public DicaAssunto(Aluno aluno, String assunto){
		this.aluno = aluno;
		this.assunto = assunto;
	}
	
	public DicaAssunto(){
		this(null, "");
	}
	
	public void setAssunto(String novoAssunto){
		this.assunto = novoAssunto;
	}
	
	public String getAssunto(){
		return assunto;
	}
	
	public String toString(){
		return "Dica de Assunto: " + getAssunto();
	}
	
	
}

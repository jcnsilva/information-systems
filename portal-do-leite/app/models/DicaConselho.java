package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class DicaConselho extends Dica{

	private String conselho;
	
	@OneToOne
	private Aluno aluno;
	
	public DicaConselho(Aluno aluno, String conselho){
		this.conselho = conselho;
	}
	
	public DicaConselho(){
		this(null, "");
	}

	public String getConselho() {
		return conselho;
	}

	public void setConselho(String conselho) {
		this.conselho = conselho;
	}
	
	@Override
	public String toString() {
		return "Conselho: " + getConselho();
	}
}

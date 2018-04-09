package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class DicaMaterial extends Dica {

	private String link;
	
	@OneToOne
	private Aluno aluno;
	
	public DicaMaterial(Aluno aluno, String link){
		this.aluno = aluno;
		this.link = link;
	}
	
	public DicaMaterial(){
		this(null, "");
	}
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	@Override
	public String toString() {
		return "Dica de material: " + getLink();
	}

}

package models;

import javax.persistence.*;

@Entity
public class Opiniao {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private boolean concorda;
	
	private String textoDiscordancia;
	
	public Opiniao(boolean concorda, String textoDiscordancia){
		this.concorda = concorda;
		this.textoDiscordancia = textoDiscordancia;
	}
	
	public Opiniao(){
		this(true, "");
	}

	public boolean getConcordancia() {
		return concorda;
	}

	public void setConcordancia(boolean concorda) {
		this.concorda = concorda;
	}

	public String getTextoDiscordancia() {
		return textoDiscordancia;
	}

	public void setTextoDiscordancia(String textoDiscordancia) {
		this.textoDiscordancia = textoDiscordancia;
	}

	public Long getId() {
		return id;
	}
	
	
	
}

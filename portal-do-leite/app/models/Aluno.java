package models;

import javax.persistence.*;

@Entity
public class Aluno {

	@Id
	@GeneratedValue
	@Column(name="ALUNO_ID")
	private Long id;
	
	private String nome;	
	private String username;
	private String senha;
	
	public Aluno(String nome, String username, String senha){
		this.nome= nome;
		this.username = username;
		this.senha = senha;
	}
	
	public Aluno(){
		this("","","");
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		Aluno other = (Aluno) obj;
		if (username == null && other.username != null)
			return false;
		else 
			return username.equals(other.username);
	}
	
	
	
}

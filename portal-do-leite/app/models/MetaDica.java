package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;

@Entity
public class MetaDica extends Dica {
	
	@ElementCollection
	@CollectionTable(name="DICAS_METADICA")
    @Column(name="M_DICAS")
	private List<Dica> dicas;
	
	private String comentario;
	
	public MetaDica(){
		this.dicas = new ArrayList<Dica>();
		this.comentario = "";
	}

	public void addDica(Dica dica) {
		dicas.add(dica);
	}

	public String getComentario() {
		return comentario;
	}
	
	public void addDicas(List<Dica> dicas){
		dicas.addAll(dicas);
	}
	
	public List<Dica> getDicas() {
		return this.dicas;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Override
	public String toString() {
		String result = "Metadica \n";
		Iterator<Dica> it = dicas.iterator();
		while(it.hasNext()){
			result += it.next().toString() + "\n";
		}
		
		result += "Comentário: " + comentario;
		
		return result;
	}

}

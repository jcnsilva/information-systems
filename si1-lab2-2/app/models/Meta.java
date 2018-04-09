/**
 * 
 */
package models;

import com.google.common.base.Objects;

import javax.persistence.*;


/**
 * @author Júlio César Neves at 19/11/2014 20:16:27
 *
 */

@Entity
public class Meta implements Comparable<Meta>{

    public enum Prioridade{
        ALTA(1), NORMAL(0), BAIXA(-1);

        private int value;

        Prioridade(int newValue){
            value = newValue;
        }

        public int getNumericValue(){
            return value;
        }
    }

    @Id
    @GeneratedValue
    private Long id;

    private String nome;
    private String descricao;
    private Prioridade prioridade;
    private boolean metaAlcancada;

    public Meta(){
        metaAlcancada = false;
    }

    public Meta(String nome, String descricao, Prioridade prioridade){
        this();
        this.nome = nome;
        this.descricao = descricao;
        this.prioridade = prioridade;
    }

    public Long getId(){
        return id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){ return nome; }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setPrioridade(String priori){
        switch (priori){
            case "alta":
                prioridade = Prioridade.ALTA;
                break;
            case "normal":
                prioridade = Prioridade.NORMAL;
                break;
            default:
                prioridade = Prioridade.BAIXA;
        }
    }

    public Prioridade getPrioridade(){
        return prioridade;
    }

    public void setAlcancada(boolean status){
        metaAlcancada = status;
    }

    public boolean getAlcancada(){
        return metaAlcancada;
    }

    @Override
    public int compareTo(Meta outra) {
        return outra.getPrioridade().getNumericValue() - this.getPrioridade().getNumericValue();
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null || !(obj instanceof Meta))
            return false;

        Meta meta2 = (Meta) obj;
        return meta2.getNome().equals(this.getNome());
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(this.nome);
    }


}

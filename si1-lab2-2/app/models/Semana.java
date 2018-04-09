package models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import static java.util.GregorianCalendar.*;

@Entity
public class Semana implements Comparable<Semana>{
	
	@Id
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Calendar startDate;
	
	@Temporal(TemporalType.DATE)
	private Calendar endDate;
	
	@OneToMany(cascade={CascadeType.ALL})
	@JoinColumn(name="METAS")
	private List<Meta> metas = new ArrayList<>();
	
	public Semana(){}
	
	public Semana(GregorianCalendar start) {
		int dayOfWeek = start.get(DAY_OF_WEEK);
		
		//Seleciona o inicio da semana para o domingo anterior à data especificada
		switch(dayOfWeek){
		case MONDAY:
			start.add(DAY_OF_WEEK, -1);
			break;
		case TUESDAY:
			start.add(DAY_OF_WEEK, -2);
			break;
		case WEDNESDAY:
			start.add(DAY_OF_WEEK, -3);
			break;
		case THURSDAY:
			start.add(DAY_OF_WEEK, -4);
			break;
		case FRIDAY:
			start.add(DAY_OF_WEEK, -5);
			break;
		case SATURDAY:
			start.add(DAY_OF_WEEK, -6);
			break;
		}
		
		Date sDate = start.getTime();
		
		startDate = new GregorianCalendar();
		startDate.setTime(sDate);
		
		endDate = new GregorianCalendar();
		endDate.setTime(sDate);
		endDate.add(DAY_OF_MONTH, 6);
		
		setId();
	}
	
	private void setId(){
		String idString = String.format("%02d", startDate.get(DAY_OF_MONTH)) + 
					String.format("%02d", startDate.get(MONTH) + 1) + 
					String.format("%04d", startDate.get(YEAR)) +
					String.format("%02d", endDate.get(DAY_OF_MONTH)) + 
					String.format("%02d", endDate.get(MONTH) + 1) + 
					String.format("%04d", endDate.get(YEAR));
		
		id =  Long.parseLong(idString);
	}
	
	public Long getId(){
		return id;
	}
	
	public String startDateAsString(){
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		return fmt.format(startDate.getTime());
	}
	
	public String endDateAsString(){
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		return fmt.format(endDate.getTime());
	}
	
	public String intervalAsString(){
		return startDateAsString() + " - " + endDateAsString();
	}
	
	public List<Meta> getMetas(){
		return metas;
	}
	
	public int totalMetas(){
		return metas.size();
	}
	
	public void addMeta(Meta novaMeta){
		metas.add(novaMeta);
	}
	
	public void deleteMeta(Meta metaAlvo){
		metas.remove(metaAlvo);
	}
	
	public boolean isEmpty(){
		return metas.isEmpty();
	}
	
	public int totalMetasAlcancadas(){
		int metasAlcancadas = 0;
		
		Iterator<Meta> it = metas.iterator(); 
		while(it.hasNext()){
			Meta atual = it.next();
			if(atual.getAlcancada() == true)
				metasAlcancadas++;
		}
		
		return metasAlcancadas;
	}
	
	public int totalMetasNaoAlcancadas(){
		return this.totalMetas() - this.totalMetasAlcancadas();
	}

	@Override
	public int compareTo(Semana outraSemana) {
		return this.startDate.compareTo(outraSemana.startDate);
	}

}
